package org.jala.university.presentation.controller.External;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import org.jala.university.commons.presentation.BaseController;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
public class EditingServiceController extends BaseController{

    public TextField serviceNameField;
    @FXML
    private ToggleGroup cpfCnpjGroup;

    @FXML
    private ToggleButton cpfToggleButton;

    @FXML
    private ToggleButton cnpjToggleButton;

    @FXML
    public TextField amountField;

    @FXML
    private TextField dueDateField;

    @FXML
    private DatePicker startDateField;

    @FXML
    private DatePicker endDateField;

    @FXML
    private TextField recipientField;

    @FXML
    private TextField agencyField;

    @FXML
    private TextField accountField;

    @FXML
    private Pane mainContent;

    private final DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MM/yyyy");

    @FXML
    public void initialize() {
        cpfToggleButton.setToggleGroup(cpfCnpjGroup);
        cnpjToggleButton.setToggleGroup(cpfCnpjGroup);

        // Configure the display text for month/year in endDateField
        endDateField.setPromptText("MM/YYYY");
        endDateField.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return monthYearFormatter.format(YearMonth.from(date));
                }
                return "";
            }

            @Override
            public LocalDate fromString(String string) {
                try {
                    YearMonth ym = YearMonth.parse(string, monthYearFormatter);
                    return ym.atDay(1);
                } catch (DateTimeParseException e) {
                    return null;
                }
            }
        });

        // Disable manual input for DatePicker fields
        startDateField.getEditor().setDisable(true);
        endDateField.getEditor().setDisable(true);

        recipientField.setEditable(false);  // Disable input until a button is selected

        cpfToggleButton.setOnAction(event -> {
            recipientField.clear();
            recipientField.setPromptText("Enter 11 digits");
            recipientField.setEditable(true);
        });

        cnpjToggleButton.setOnAction(event -> {
            recipientField.clear();
            recipientField.setPromptText("Enter 14 digits");
            recipientField.setEditable(true);
        });

        amountField.addEventFilter(KeyEvent.KEY_TYPED, event -> formatAmountField());
        amountField.addEventFilter(KeyEvent.KEY_TYPED, this::validateNumericInput);
        agencyField.addEventFilter(KeyEvent.KEY_TYPED, this::validateNumericInput);
        accountField.addEventFilter(KeyEvent.KEY_TYPED, this::validateNumericInput);
        recipientField.addEventFilter(KeyEvent.KEY_TYPED, this::validateRecipientInput);
        dueDateField.addEventFilter(KeyEvent.KEY_TYPED, this::validateNumericInput);

        serviceNameField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() > 20) {
                serviceNameField.setText(oldText);
            }
        });

        agencyField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() > 4) {
                agencyField.setText(oldText);
            }
        });

        accountField.textProperty().addListener((obs, oldText, newText) -> {
            // Limitar o tamanho do texto para 11 caracteres
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


        dueDateField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() > 2) {
                dueDateField.setText(oldText);
            }
        });

        recipientField.textProperty().addListener((obs, oldText, newText) -> formatRecipientField(newText));
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

    private void validateRecipientInput(KeyEvent event) {
        String currentText = recipientField.getText();
        int maxLength = cpfToggleButton.isSelected() ? 11 : 14;
        if (!event.getCharacter().matches("\\d") || currentText.length() >= maxLength) {
            event.consume();
        }
    }

    private void formatRecipientField(String text) {
        if (cpfToggleButton.isSelected()) {
            // Format as CPF: ###.###.###-##
            text = text.replaceAll("[^\\d]", "");
            if (text.length() <= 11) {
                String formattedText = text.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
                recipientField.setText(formattedText);
                recipientField.positionCaret(formattedText.length());
            }
        } else if (cnpjToggleButton.isSelected()) {
            // Format as CNPJ: ##.###.###/####-##
            text = text.replaceAll("[^\\d]", "");
            if (text.length() <= 14) {
                String formattedText = text.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
                recipientField.setText(formattedText);
                recipientField.positionCaret(formattedText.length());
            }
        }
    }


    public boolean validateAllFields() {
        if (serviceNameField.getText() == null || serviceNameField.getText().isEmpty()) {
            showAlert("Error", "Service name cannot be empty.");
            return false;
        }
        if (cpfCnpjGroup.getSelectedToggle() == null) {
            showAlert("Error", "Please select either CPF or CNPJ.");
            return false;
        }
        if (recipientField.getText().length() != (cpfToggleButton.isSelected() ? 14 : 18)) {
            showAlert("Error", "The document number must be 11 digits (CPF) or 14 digits (CNPJ).");
            return false;
        }
        if (agencyField.getText().length() != 4) {
            showAlert("Error", "Agency must be exactly 4 digits.");
            return false;
        }
        if (accountField.getText().length() < 7 || accountField.getText().length() > 12) {
            showAlert("Error", "Account number must be between 6 and 11 digits.");
            return false;
        }
        if (amountField.getText() == null || amountField.getText().isEmpty()) {
            showAlert("Error", "Amount cannot be empty.");
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
        if (dueDateField.getText().isEmpty() || !dueDateField.getText().matches("^(0?[1-9]|[12][0-9]|3[01])$")) {
            showAlert("Error", "Please enter a valid day (1-31) in the Due Date field.");
            return false;
        }
        if (startDateField.getValue() == null || !startDateField.getValue().isAfter(LocalDate.now())) {
            showAlert("Error", "Start Date must be a future date.");
            return false;
        }
        if (endDateField.getValue() != null && endDateField.getValue().isBefore(startDateField.getValue())) {
            showAlert("Error", "End Date must be after Start Date.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void schedulePaymentInformation() throws IOException {
        if (validateAllFields()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/SchedulePaymentScreens/SchedulePaymentInformation/SchedulePaymentInformation.fxml"));
            Pane schedulePaymentInformation = loader.load();

            // Obter o controlador de SchedulePaymentInformationController
            SchedulePaymentInformationController controller = loader.getController();

            // Determina se o tipo de documento selecionado é CNPJ
            boolean isCNPJ = cnpjToggleButton.isSelected();

            // Convert amountField to BigDecimal
            BigDecimal amount = new BigDecimal(amountField.getText().replace(",", "."));

            // Passar os dados para o controlador de SchedulePaymentInformationController
            controller.setPaymentData(
                    serviceNameField.getText(),
                    recipientField.getText(),
                    amount,
                    agencyField.getText(),
                    accountField.getText(),
                    dueDateField.getText(),
                    startDateField.getValue().toString(),
                    endDateField.getValue() != null ? endDateField.getValue().toString() : "",
                    isCNPJ // Passa o valor de CNPJ/CPF
            );

            mainContent.getChildren().setAll(schedulePaymentInformation);
        }
    }

    @FXML
    private void back() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/ScheduleServices/ServiceDetail.fxml"));
        Pane schedulePayment = loader.load();
        mainContent.getChildren().setAll(schedulePayment);
    }
}

