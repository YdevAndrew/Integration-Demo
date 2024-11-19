package org.jala.university.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jala.university.application.dto.CustomerDto;
import org.jala.university.application.service.CustomerService;
import org.jala.university.infrastructure.config.SpringFXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class ValidateAccountController {

    @FXML
    public PasswordField passwordField;

    @FXML
    public PasswordField repeatPasswordField;

    @FXML
    public Button validateButton;

    @FXML
    public TextField verificationCodeField;

    @FXML
    public Label infoLabel;

    public CustomerService customerService;
    public PasswordEncoder passwordEncoder;
    private Stage primaryStage;

    @Autowired
    public ValidateAccountController(CustomerService customerService, PasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
    }

    public void setPrimaryStage(Stage primaryStage) {
        if (primaryStage == null) {
            throw new IllegalArgumentException("primaryStage não pode ser null");
        }
        this.primaryStage = primaryStage;
    }

    @FXML
    public void initialize() {
        // Configurações iniciais se necessário
        validateButton.setOnAction(event -> validateCode());
    }

    @FXML
    private void validateCode() {
        infoLabel.setVisible(true);

        try {
            String enteredCode = verificationCodeField.getText();
            if (enteredCode.trim().isEmpty()) {
                infoLabel.setText("Por favor, insira o código de verificação.");
                return;
            }

            CustomerDto customerDto = customerService.getCustomerByVerificationCode(enteredCode);
            if (customerDto == null) {
                infoLabel.setText("Código de verificação inválido. Tente novamente.");
                return;
            }

            String password = passwordField.getText();
            String repeatPassword = repeatPasswordField.getText();

            if (password == null || password.isEmpty() || !password.equals(repeatPassword)) {
                infoLabel.setText("As senhas não coincidem ou estão vazias.");
                return;
            }

            String encryptedPassword = passwordEncoder.encode(password);
            customerDto.setPassword(encryptedPassword.getBytes());
            customerService.updateCustomerPassword(customerDto);

            infoLabel.setText("Conta validada e senha salva com sucesso!");

            // Usar Platform.runLater para operações de UI
            javafx.application.Platform.runLater(() -> {
                try {
                    Thread.sleep(1000);
                    loadLoginScreen();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            System.err.println("Erro ao validar conta: " + e.getMessage());
            e.printStackTrace();
            infoLabel.setText("Erro ao validar conta. Por favor, tente novamente.");
        }
    }

    private void loadLoginScreen() {
        try {
            FXMLLoader loader = SpringFXMLLoader.create("/login.fxml");
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Erro ao carregar tela de login: " + e.getMessage());
            e.printStackTrace();
            infoLabel.setText("Erro ao carregar tela de login.");
        }
    }
}