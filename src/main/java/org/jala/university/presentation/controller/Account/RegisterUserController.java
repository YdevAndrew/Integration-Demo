package org.jala.university.presentation.controller.Account;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.jala.university.application.dto.dto_account.AccountDto;
import org.jala.university.application.dto.dto_account.CustomerDto;
import org.jala.university.application.service.service_account.AccountServiceImpl;
import org.jala.university.application.service.service_account.CustomerService;
import org.jala.university.config.config_account.SpringFXMLLoader;
import org.jala.university.domain.entity.entity_account.AccountStatus;
import org.jala.university.domain.entity.entity_account.Currency;
import org.jala.university.application.service.service_account.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.jala.university.utils.EmailService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Controller
public class RegisterUserController {

    @FXML
    public TextField nameField;

    @FXML
    public TextField cpfField;

    @FXML
    public DatePicker birthDateField;

    @FXML
    public ComboBox<String> genderField;

    @FXML
    public TextField emailField;

    @FXML
    public TextField phoneField;

    @FXML
    public TextField cepField;

    @FXML
    public TextField streetField;

    @FXML
    public TextField districtField;

    @FXML
    public ComboBox<String> stateField;
    @FXML
    public ComboBox<String> countryField;

    @FXML
    public Label nameError;

    @FXML
    public Label cpfError;

    @FXML
    public Label birthDateError;

    @FXML
    public Label genderError;

    @FXML
    public Label emailError;

    @FXML
    public Label phoneError;

    @FXML
    public Label cepError;

    @FXML
    public Label streetError;

    @FXML
    public Label districtError;

    @FXML
    public Label stateError;

    @FXML
    public Label countryError;

    private final CustomerService customerService;
    private final AuthenticationService authenticationService;
    private final EmailService emailService;
    private final AccountServiceImpl accountServiceImpl;

    @Autowired
    public RegisterUserController(CustomerService customerService, AuthenticationService authenticationService, AccountServiceImpl accountServiceImpl) {
        this.customerService = customerService;
        this.authenticationService = authenticationService;
        this.emailService = new EmailService(); // Initialize EmailService
        this.accountServiceImpl = accountServiceImpl;
    }

    @FXML
    public void initialize() {
        setupGenderComboBox();
        setupCountryComboBox();
        setupStateComboBox();
    }


    @FXML
    public void handleRegister(ActionEvent event) {
        try {
            validateInputs();
            CustomerDto customerDto = createCustomerDto();
            String verificationCode = generateVerificationCode();
            customerDto.setVerificationCode(verificationCode);

            // Create customer first
            CustomerDto savedCustomer = customerService.create(customerDto);

            // Create bank account with customer ID
            AccountDto accountDto = AccountDto.builder()
                    .accountNumber(accountServiceImpl.generateAccountNumber())
                    .balance(BigDecimal.ZERO)
                    .status(AccountStatus.ACTIVE)
                    .currency(Currency.BRL)
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .paymentPassword("1234")
                    .build();

            // Create the account and associate it with the customer
            accountServiceImpl.createAccount(accountDto, savedCustomer.getId());

            sendVerificationEmail(savedCustomer.getEmail(), verificationCode);

            // Load validation screen
            FXMLLoader loader = SpringFXMLLoader.create("/Account/validate-account.fxml");
            Parent root = loader.load();

            ValidateAccountController validateController = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            validateController.setPrimaryStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Error during registration: " + e.getMessage());
            e.printStackTrace();
            showError(e.getMessage());
        }
    }

    private String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(900000) + 100000);
    }

    private void sendVerificationEmail(String email, String verificationCode) {
        String subject = "JalaU Bank - Email Verification Code";
        String messageText = String.format(
                "Welcome to JalaU Bank!\n\n" +
                        "Your verification code is: %s\n\n" +
                        "Please use this code to verify your account.\n" +
                        "If you didn't request this code, please ignore this email.\n\n" +
                        "Best regards,\n" +
                        "JalaU Bank Team",
                verificationCode
        );

        String result = emailService.sendEmail(email, subject, messageText);

        if (!result.startsWith("Mail sent")) {
            throw new RuntimeException("Failed to send verification email: " + result);
        }
    }

    private void setupGenderComboBox() {
        genderField.getItems().addAll("Male", "Female", "Other");
    }

    public void setupCountryComboBox() {
        countryField.getItems().addAll("Brazil", "Other");
    }

    public void setupStateComboBox() {
        stateField.getItems().addAll("AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO");
    }

//    private void validateInputs() {
//        if (nameField.getText().trim().isEmpty()) {
//            throw new RuntimeException("Name is mandatory");
//        }
//
//        if (genderField.getValue() == null) {
//            throw new RuntimeException("Gender is mandatory");
//        }
//
//        if (birthDateField.getValue() == null) {
//            throw new RuntimeException("Birth date is mandatory");
//        }
//
//        if (!validateEmail()) {
//            throw new RuntimeException("Invalid email address");
//        }
//    }

    @FXML
    private void validateInputs() {
        boolean hasError = false;

        // Validar campo Nome
        if (nameField.getText().trim().isEmpty()) {
            nameError.setText("Name is mandatory");
            nameError.setStyle("-fx-text-fill: red;");
            hasError = true;
        } else {
            nameError.setText("");
        }

        // Validar campo CPF
        if (cpfField.getText().trim().isEmpty()) {
            cpfError.setText("CPF is mandatory");
            cpfError.setStyle("-fx-text-fill: red;");
            hasError = true;
        } else {
            cpfError.setText("");
        }

        // Validar campo Data de Nascimento (Birthday)
        if (birthDateField.getValue() == null) {
            birthDateError.setText("Birthday is mandatory");
            birthDateError.setStyle("-fx-text-fill: red;");
            hasError = true;
        } else {
            birthDateError.setText("");
        }

        // Validar campo Gênero (Gender)
        if (genderField.getValue() == null || genderField.getValue().toString().trim().isEmpty()) {
            genderError.setText("Gender is mandatory");
            genderError.setStyle("-fx-text-fill: red;");
            hasError = true;
        } else {
            genderError.setText("");
        }

        // Validar campo Email
        if (emailField.getText().trim().isEmpty()) {
            emailError.setText("Email is mandatory");
            emailError.setStyle("-fx-text-fill: red;");
            hasError = true;
        } else if (!emailField.getText().matches("^\\S+@\\S+\\.\\S+$")) {
            emailError.setText("Invalid email format");
            emailError.setStyle("-fx-text-fill: red;");
            hasError = true;
        } else {
            emailError.setText("");
        }

        // Validar campo Telefone (Phone)
        if (phoneField.getText().trim().isEmpty()) {
            phoneError.setText("Phone is mandatory");
            phoneError.setStyle("-fx-text-fill: red;");
            hasError = true;
        } else if (!phoneField.getText().matches("^\\(\\d{2}\\) \\d{5}-\\d{4}$")) {
            phoneError.setText("Invalid phone format");
            phoneError.setStyle("-fx-text-fill: red;");
            hasError = true;
        } else {
            phoneError.setText("");
        }

        // Validar campo CEP (Postal Code)
        if (cepField.getText().trim().isEmpty()) {
            cepError.setText("Postal Code is mandatory");
            cepError.setStyle("-fx-text-fill: red;");
            hasError = true;
        } else if (!cepField.getText().matches("^\\d{5}-\\d{3}$")) {
            cepError.setText("Invalid postal code format");
            cepError.setStyle("-fx-text-fill: red;");
            hasError = true;
        } else {
            cepError.setText("");
        }

        // Validar campo Rua (Street Address)
        if (streetField.getText().trim().isEmpty()) {
            streetError.setText("Street Address is mandatory");
            streetError.setStyle("-fx-text-fill: red;");
            hasError = true;
        } else {
            streetError.setText("");
        }

        // Validar campo Distrito (District)
        if (districtField.getText().trim().isEmpty()) {
            districtError.setText("District is mandatory");
            districtError.setStyle("-fx-text-fill: red;");
            hasError = true;
        } else {
            districtError.setText("");
        }

        // Validar campo Estado (State)
        if (stateField.getValue() == null || stateField.getValue().toString().trim().isEmpty()) {
            stateError.setText("State is mandatory");
            stateError.setStyle("-fx-text-fill: red;");
            hasError = true;
        } else {
            stateError.setText("");
        }

        // Validar campo País (Country)
        if (countryField.getValue() == null || countryField.getValue().toString().trim().isEmpty()) {
            countryError.setText("Country is mandatory");
            countryError.setStyle("-fx-text-fill: red;");
            hasError = true;
        } else {
            countryError.setText("");
        }

        // Se não houver erros, continue com o processamento
        if (!hasError) {
            System.out.println("All fields are valid. Proceeding...");
        }
    }


        private CustomerDto createCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFullName(nameField.getText().trim());
        customerDto.setCpf(cpfField.getText());
        customerDto.setGender(genderField.getValue());
        customerDto.setBirthday(birthDateField.getValue());
        customerDto.setEmail(emailField.getText().trim());
        customerDto.setPhoneNumber(phoneField.getText().trim());
        customerDto.setDistrict(districtField.getText());
        customerDto.setStreet(streetField.getText());
        customerDto.setState(stateField.getValue());
        customerDto.setCountry(countryField.getValue());
        customerDto.setPostalCode(cepField.getText().trim());


        return customerDto;
    }

    private void showError(String message) {
        System.out.println("Error: " + message);
    }

    private void showSuccess() {
        Platform.runLater(() -> {
            nameError.setText("Registration completed successfully!");
            nameError.setVisible(true);
        });
    }

    @FXML
    public void handleBackClick(MouseEvent mouseEvent) {
        try {
            // Carregar o arquivo FXML da tela anterior (exemplo: home-view.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Account/home-view.fxml"));
            Parent root = loader.load();

            // Obter o Stage atual a partir do evento do botão de volta
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            // Configurar a nova cena e exibi-la
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validateAll() {
        try {
            validateInputs();
            return true;
        } catch (RuntimeException e) {
            showError(e.getMessage());
            return false;
        }
    }

    public boolean validateCPF() {
        boolean isValid = cpfField.getText().trim().matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
        Platform.runLater(() -> {
            if (!isValid) {
                cpfError.setText("Invalid CPF");
                cpfError.setVisible(true);
            } else {
                cpfError.setText("Invalid CPF");
                cpfError.setVisible(false);
            }
        });
        return isValid;
    }

    public boolean validateEmail() {
        boolean isValid = emailField.getText().trim().matches("^[A-Za-z0-9+_.-]+@(.+)$");
        Platform.runLater(() -> {
            if (!isValid) {
                emailError.setText("Invalid Email address");
                emailError.setVisible(true);
            } else {
                emailError.setText("");
                emailError.setVisible(false);
            }
        });
        return isValid;
    }

    public void showHelpModal(ActionEvent event) {
        try {
            // Carregar o FXML do modal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Account/help.fxml"));
            Parent root = loader.load();

            // Criar um novo Stage para o modal
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            modalStage.setTitle("Help");

            // Configurar a cena do modal
            Scene scene = new Scene(root);
            scene.setFill(null);
            modalStage.setScene(scene);

            // Exibir o modal
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
