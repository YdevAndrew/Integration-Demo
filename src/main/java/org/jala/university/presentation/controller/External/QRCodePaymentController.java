package org.jala.university.presentation.controller.External;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.jala.university.commons.presentation.BaseController;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
public class QRCodePaymentController extends BaseController {

    @FXML
    public ImageView qrCodeImageView; // Exibe a imagem do QR Code carregada
    @FXML
    public Label statusLabel; // Exibe mensagens de status, como erro ou sucesso
    @FXML
    public Button manualEntryButton; // Botão para inserção manual
    @FXML
    public Button cancelButton; // Botão Cancel
    public TextField agencyField;
    public TextField recipientField;
    public RadioButton cnpjToggleButton;
    public TextField accountField;
    public TextField amountField;
    public DatePicker startDateField;
    public DatePicker endDateField;
    public ToggleGroup cpfCnpjGroup;
    public RadioButton cpfToggleButton;
    public TextField serviceDescriptionField;
    @FXML
    private Pane mainContent;

    // Variáveis para armazenar os dados extraídos do QR Code
    public double amount;
    public String receiverName;
    public String agency;
    public String account;
    public String expirationDate;
    public String cnpjReceiver;

    // Método para lidar com o clique no botão "Select your QRCode"
    @FXML
    public void onSelectQRCodeButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            qrCodeImageView.setImage(image);
            statusLabel.setText("Valid QRcode!");
            statusLabel.setStyle("-fx-text-fill: green;");
            manualEntryButton.setVisible(false);
            decodeQRCode(file);
        } else {
            statusLabel.setText("No images selected.");
            statusLabel.setStyle("-fx-text-fill: red;");
            manualEntryButton.setVisible(true);
        }
    }

    // Método para tentar decodificar o QR Code e validar os dados
    public void decodeQRCode(File file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            QRCodeReader reader = new QRCodeReader();
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = reader.decode(bitmap);

            String qrCodeContent = result.getText();
            System.out.println("QR Code Content: " + qrCodeContent);

            try {
                JSONObject json = new JSONObject(qrCodeContent);

                if (!json.has("amount") || !json.has("cnpjReceiver") || !json.has("nameReceiver") ||
                        !json.has("agencyReceiver") || !json.has("accountReceiver") || !json.has("expiredDate")) {
                    throw new Exception("Mandatory fields missing in QR Code.");
                }

                amount = json.getDouble("amount");
                cnpjReceiver = json.getString("cnpjReceiver");
                receiverName = json.getString("nameReceiver");
                agency = json.getString("agencyReceiver");
                account = json.getString("accountReceiver");

                // Validação do formato da data
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(json.getString("expiredDate"), formatter);
                    expirationDate = date.toString();
                } catch (DateTimeParseException e) {
                    throw new Exception("Invalid date format. Expected yyyy-MM-dd.");
                }

            } catch (Exception e) {
                System.out.println("Invalid QR Code: " + e.getMessage());
                statusLabel.setText("Invalid QR Code!");
                statusLabel.setStyle("-fx-text-fill: red;");
                manualEntryButton.setVisible(true);
            }

        } catch (Exception e) {
            System.out.println("Error reading or decoding the QR Code: " + e.getMessage());
            statusLabel.setText("Error processing QR Code.");
            statusLabel.setStyle("-fx-text-fill: red;");
            manualEntryButton.setVisible(true);
        }
    }

    @FXML
    public void onConfirmButtonClick(ActionEvent event) {
        if (amount > 0 && receiverName != null && !receiverName.isEmpty() &&
                agency != null && !agency.isEmpty() && account != null && !account.isEmpty() && expirationDate != null) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/ManualPaymentScreens/ManualPaymentInformation/ManualPaymentInformation.fxml"));
                Pane paymentDetailsPane = loader.load();

                ManualPaymentInformationController paymentDetailsController = loader.getController();
                if (paymentDetailsController != null) {
                    paymentDetailsController.setQRCodePaymentController(this);
                    paymentDetailsController.initializePaymentDetails(amount, receiverName, agency, account, expirationDate, cnpjReceiver);
                } else {
                    System.out.println("Error: PaymentDetailsController controller was not injected correctly.");
                }

                mainContent.getChildren().setAll(paymentDetailsPane);

            } catch (IOException e) {
                e.printStackTrace();
                statusLabel.setText("Error loading payment screen.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } else {
            statusLabel.setText("Please enter a valid QR Code..");
            statusLabel.setStyle("-fx-text-fill: red;");
            manualEntryButton.setVisible(true);
        }
    }

    public void showManualEntryButton() {
        if (manualEntryButton != null) {
            manualEntryButton.setVisible(true);
        }
    }

    @FXML
    public void onManualEntryClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/PaymentModule/ManualPaymentScreens/QRCodePayment/ManuallyInsert.fxml"));
            Pane manualInsertPane = loader.load();

            ManuallyInsertController manuallyInsertController = loader.getController();
            mainContent.getChildren().setAll(manualInsertPane);
            manuallyInsertController.setMainContent(mainContent);

        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error loading manual entry screen");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    public void onCancelButtonClick(ActionEvent event) {
        System.out.println("'Cancel' button was clicked!");
        showManualEntryButton();
    }
}
