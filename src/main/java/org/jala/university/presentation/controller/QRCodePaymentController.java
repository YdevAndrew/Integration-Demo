package org.jala.university.presentation.controller;

import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.qrcode.QRCodeReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;

import org.jala.university.application.service.util.QRCodeValidator;
import org.jala.university.application.service.util.ValidationResult;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class QRCodePaymentController {

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
        // Cria um FileChooser para permitir ao usuário escolher um arquivo de imagem
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png")); // Limita a escolha para arquivos PNG

        // Exibe a janela para selecionar o arquivo
        File file = fileChooser.showOpenDialog(null);  // Ou use o Stage atual caso queira que a janela de arquivo esteja associada ao seu palco atual

        if (file != null) {
            // Se o usuário selecionar um arquivo, carrega a imagem e exibe no ImageView
            Image image = new Image(file.toURI().toString());
            qrCodeImageView.setImage(image);  // Exibe a imagem no ImageView
            statusLabel.setText("Image uploaded successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");  // Mensagem de sucesso em verde
            manualEntryButton.setVisible(false); // Esconde o botão de inserção manual
            // Tenta decodificar o QR Code
            decodeQRCode(file);
        } else {
            // Se o usuário não selecionar nada
            statusLabel.setText("No images selected.");
            statusLabel.setStyle("-fx-text-fill: red;");  // Mensagem de erro em vermelho
            manualEntryButton.setVisible(true);
        }
    }

    // Método para tentar decodificar o QR Code e exibir os dados no terminal
    public void decodeQRCode(File file) {
        try {
            // Lê o arquivo de imagem
            BufferedImage bufferedImage = ImageIO.read(file);

            // Cria um leitor de QR Code usando a biblioteca ZXing
            QRCodeReader reader = new QRCodeReader();

            // Converte a imagem para um formato binarizado
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            // Decodifica o QR Code
            Result result = reader.decode(bitmap);

            // Exibe o conteúdo do QR Code no terminal
            String qrCodeContent = result.getText();
            System.out.println("QR Code Content: " + qrCodeContent);

            // Tenta converter o conteúdo para JSON e exibe no terminal
            try {
                JSONObject json = new JSONObject(qrCodeContent);

                // Verifica se todos os campos necessários estão presentes no JSON
                if (!json.has("amount") || !json.has("cnpjReceiver") || !json.has("nameReceiver") ||
                        !json.has("agencyReceiver") || !json.has("accountReceiver") || !json.has("expiredDate")) {
                    throw new Exception("Mandatory fields missing in QR Code.");
                }

                // Armazena os dados extraídos
                amount = json.getDouble("amount");
                cnpjReceiver = json.getString("cnpjReceiver");
                // Armazena o CNPJ formatado
//                cnpjReceiver = QRCodeValidator.formatCNPJ(json.getString("cnpjReceiver"));
                receiverName = json.getString("nameReceiver");
                agency = json.getString("agencyReceiver");
                account = json.getString("accountReceiver");
                expirationDate = json.getString("expiredDate");

                // Valida os dados extraídos
                ValidationResult validationResult = QRCodeValidator.validateQRCodeData(
                        amount, cnpjReceiver, receiverName, agency, account, expirationDate
                );

                if (validationResult.isValid()) {
                    // Se o QR Code for válido, atualiza o status para sucesso
                    statusLabel.setText("Valid QR Code!");
                    statusLabel.setStyle("-fx-text-fill: green;");
                    manualEntryButton.setVisible(false); // Esconde o botão de inserção manual
                } else {
                    // Se o QR Code for inválido, exibe a mensagem de erro
                    statusLabel.setText(validationResult.getMessage());
                    statusLabel.setStyle("-fx-text-fill: red;");
                    manualEntryButton.setVisible(true); // Torna o botão de inserção manual visível
                }

            } catch (Exception e) {
                // Se ocorrer um erro (campo ausente ou mal formado)
                System.out.println("Invalid QR Code: " + e.getMessage());
                statusLabel.setText("Invalid QR Code!");
                statusLabel.setStyle("-fx-text-fill: red;"); // Exibe a mensagem de erro na interface
                manualEntryButton.setVisible(true); // Torna o botão de inserção manual visível
            }

        } catch (Exception e) {
            System.out.println("Error reading or decoding the QR Code: " + e.getMessage());
            statusLabel.setText("Error processing QR Code.");
            statusLabel.setStyle("-fx-text-fill: red;");
            manualEntryButton.setVisible(true); // Torna o botão de inserção manual visível
        }
    }



    @FXML
    public void onConfirmButtonClick(ActionEvent event) {
        if (amount > 0 && receiverName != null && !receiverName.isEmpty() &&
                agency != null && !agency.isEmpty() && account != null && !account.isEmpty() && expirationDate != null) {

            try {
                // Carrega a nova tela de exibição dos detalhes do pagamento (como um container dentro da tela atual)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ManualPaymentScreens/ManualPaymentInformation/ManualPaymentInformation.fxml"));
                Pane paymentDetailsPane = loader.load();

                // Inicializa o controlador da nova tela com os dados
                ManualPaymentInformationController paymentDetailsController = loader.getController();
                if (paymentDetailsController != null) {
                    // Passa a referência do controlador anterior
                    paymentDetailsController.setQRCodePaymentController(this);
                    paymentDetailsController.initializePaymentDetails(amount, receiverName, agency, account, expirationDate, cnpjReceiver);
                } else {
                    System.out.println("Error: PaymentDetailsController controller was not injected correctly.");
                }

                // Substitui o conteúdo atual da tela com o painel de detalhes de pagamento
                mainContent.getChildren().setAll(paymentDetailsPane);

            } catch (IOException e) {
                e.printStackTrace();
                statusLabel.setText("Error loading payment screen.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } else {
            statusLabel.setText("Please enter a valid QR Code..");
            statusLabel.setStyle("-fx-text-fill: red;");
            manualEntryButton.setVisible(true); // Torna o botão de inserção manual visível
        }
    }

    // Método para exibir o botão de inserção manual
    public void showManualEntryButton() {
        if (manualEntryButton != null) {
            manualEntryButton.setVisible(true);  // Torna o botão visível
        }
    }

    // Método para lidar com a inserção manual dos dados (se o usuário preferir não usar o QR Code)
    @FXML
    public void onManualEntryClick(ActionEvent event) {
        System.out.println("Insert Manually' button has been clicked!");
        System.out.println("Open manual entry screen...");

        try {
            // Carrega o arquivo FXML da tela de inserção manual
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ManualPaymentScreens/QRCodePayment/ManuallyInsert.fxml"));
            Pane manualInsertPane = loader.load();

            // Obtém o controlador associado à tela ManuallyInsert
            ManuallyInsertController manuallyInsertController = loader.getController();

            // Substitui o conteúdo do painel principal pela nova tela
            mainContent.getChildren().setAll(manualInsertPane);

            // Configura o controlador com dependências, se necessário
            manuallyInsertController.setMainContent(mainContent);

        } catch (IOException e) {
            e.printStackTrace();
            // Exibe uma mensagem de erro na interface (ou no console)
            statusLabel.setText("Error loading manual entry screen");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }


    // Método para lidar com o clique no botão Cancel
    @FXML
    public void onCancelButtonClick(ActionEvent event) {
        // Exibe o botão de inserção manual quando o botão Cancel é pressionado
        System.out.println("'Cancel' button was clicked!");
        showManualEntryButton();  // Torna o botão de inserção manual visível
    }


}
