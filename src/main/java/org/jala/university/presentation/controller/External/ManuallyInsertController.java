package org.jala.university.presentation.controller.External;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.jala.university.commons.presentation.BaseController;
import org.jala.university.presentation.controller.Loan.SpringFXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ManuallyInsertController extends BaseController {

    @Autowired
    SpringFXMLLoader springFXMLLoader;
    @FXML
    private TextField nameField;
    @FXML
    private TextField amountField;
    @FXML
    private TextField recipientField;
    @FXML
    private TextField agencyField;
    @FXML
    private TextField accountField;
    @FXML
    private DatePicker dueDateField;

    public LocalDateTime transactionDate;
    public LocalDate startDate;
    public LocalDate endDate;
    public String description;
    @FXML
    private Pane mainContent;

    public void setMainContent(Pane mainContent) {
        this.mainContent = mainContent;
    }

    @FXML
    public void initialize() {
        if (nameField != null) {
            nameField.setText("External User");
        }
        // Disable manual input for DatePicker fields
        dueDateField.getEditor().setDisable(true);

        amountField.addEventFilter(KeyEvent.KEY_TYPED, event -> formatAmountField());
        amountField.addEventFilter(KeyEvent.KEY_TYPED, this::validateNumericInput);
        agencyField.addEventFilter(KeyEvent.KEY_TYPED, this::validateNumericInput);
        accountField.addEventFilter(KeyEvent.KEY_TYPED, this::validateNumericInput);

        agencyField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() > 4) {
                agencyField.setText(oldText);
            }
        });

        accountField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() > 12) {
                accountField.setText(oldText);
                return;
            }

            // Remover o hífen antigo para que a formatação seja aplicada de maneira consistente
            String plainText = newText.replace("-", "");

            // Adicionar o hífen antes do último dígito quando tiver entre 6 e 11 dígitos
            if (plainText.length() >= 6) {
                accountField.setText(plainText.substring(0, plainText.length() - 1) + "-" + plainText.substring(plainText.length() - 1));
                accountField.positionCaret(accountField.getText().length()); // Posiciona o cursor no final do texto
            } else {
                accountField.setText(plainText);
            }
        });

        recipientField.textProperty().addListener((obs, oldText, newText) -> {
            formatRecipientField(newText);
        });
    }

    private void formatAmountField() {
        String text = amountField.getText().replace(",", "").replace(".", "");
        if (!text.matches("\\d*")) {
            amountField.setText(text.replaceAll("[^\\d]", ""));
        }
        if (text.length() > 1) {
            amountField.setText(text.substring(0, text.length() - 1) + "," + text.substring(text.length() - 1));
            amountField.positionCaret(amountField.getText().length());
        }
    }

    private void validateNumericInput(KeyEvent event) {
        if (!event.getCharacter().matches("\\d")) {
            event.consume();
        }
    }

    private void formatRecipientField(String text) {
        String sanitizedText = text.replaceAll("[^\\d]", "");
        if (sanitizedText.length() > 14) {
            sanitizedText = sanitizedText.substring(0, 14);
        }
        if (sanitizedText.length() == 14) {
            sanitizedText = sanitizedText.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        }
        recipientField.setText(sanitizedText);
    }

    @FXML
    private void manualPaymentInformation() throws IOException {
        if (validateAllFields()) {
            try {
                // Carrega a nova tela de exibição dos detalhes do pagamento (como um container dentro da tela atual)
                FXMLLoader loader = springFXMLLoader.load("/External/ManualPaymentScreens/ManualPaymentInformation/ManualPaymentInformation.fxml");
                Pane paymentDetailsPane = loader.load();

                // Inicializa o controlador da nova tela com os dados
                ManualPaymentInformationController paymentDetailsController = loader.getController();
                if (paymentDetailsController != null) {
                    // Converte o valor do campo de texto para double
                    double amount = Double.parseDouble(amountField.getText().replace(",", "."));
                    // Passa a referência do controlador anterior
                    paymentDetailsController.setManuallyInsertController(this);
                    paymentDetailsController.initializePaymentDetails(
                            BigDecimal.valueOf(amount),
                        nameField.getText(),
                        agencyField.getText(),
                        accountField.getText(),
                        getDueDateFormatted(),  // Modificação para pegar a data formatada ou null
                        recipientField.getText(),
                        transactionDate,
                        description,
                        startDate,
                        endDate);
                } else {
                    System.out.println("Error: PaymentDetailsController controller was not injected correctly.");
                }

                // Substitui o conteúdo atual da tela com o painel de detalhes de pagamento
                mainContent.getChildren().setAll(paymentDetailsPane);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean validateAllFields() {
        if (!recipientField.getText().matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")) {
            showAlert("Error", "The recipient must be a valid CNPJ.");
            return false;
        }
        if (agencyField.getText().length() != 4) {
            showAlert("Error", "Agency must be exactly 4 digits.");
            return false;
        }
        if (accountField.getText().length() < 6 || accountField.getText().length() > 12) {
            showAlert("Error", "Account must be between 6 and 11 digits.");
            return false;
        }
        if (amountField.getText() == null || amountField.getText().isEmpty()) {
            showAlert("Error", "Amount cannot be empty.");
            return false;
        }
        if (dueDateField.getValue() == null) {
            showAlert("Error", "Due Date cannot be empty.");
            return false;
        }
        try {
            double amount = Double.parseDouble(amountField.getText().replace(",", "."));
            if (amount < 1) {
                showAlert("Error", "Amount must be at least 1.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid amount.");
            return false;
        }
        return true;
    }

    private String getDueDateFormatted() {
        if (dueDateField.getValue() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return dueDateField.getValue().format(formatter);
        }
        return null; // Caso a data seja null
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void back() throws IOException {
        FXMLLoader loader = springFXMLLoader.load("/External/ManualPaymentScreens/QRCodePayment/QRCodePayment.fxml");
        Pane schedulePayment = loader.load();
        mainContent.getChildren().setAll(schedulePayment);
    }
}
