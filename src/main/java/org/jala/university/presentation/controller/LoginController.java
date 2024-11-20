package org.jala.university.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;
import org.jala.university.application.service.AuthenticationService;
import org.jala.university.ServiceFactory;
import org.jala.university.application.service.CustomerService;
import org.jala.university.infrastructure.config.SpringFXMLLoader;
import org.jala.university.presentation.AccountView;
import org.jala.university.commons.presentation.ViewSwitcher;
import org.jala.university.utils.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

@Setter
@Controller
public class LoginController {
    @FXML
    public Button loginButton;

    @FXML
    TextField cpfField;

    @FXML
    Label errorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField visiblePasswordField;

    @FXML
    private ImageView togglePasswordVisibility;

    private boolean isPasswordVisible = false;

    private CustomerService customerService;


    @Autowired
    public LoginController(CustomerService service) {
        this.customerService = service;
    }



    @FXML
    public void initialize() {
        errorLabel.setVisible(false);
        loginButton.setOnAction(event -> handleLogin());

        // Configurações iniciais para alternância de visibilidade da senha
        visiblePasswordField.setVisible(false);  // Esconde o campo de texto visível
        visiblePasswordField.managedProperty().bind(visiblePasswordField.visibleProperty());
        passwordField.managedProperty().bind(passwordField.visibleProperty());

        // Configuração de imagem do ícone de visibilidade da senha
        togglePasswordVisibility.setImage(new Image("assets/assets/login/eye-line.png"));
        togglePasswordVisibility.setOnMouseClicked(this::togglePasswordVisibility);

        // Sincronizar texto entre os campos de senha e senha visível
        visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());
    }

    private void togglePasswordVisibility(MouseEvent event) {
        isPasswordVisible = !isPasswordVisible;

        if (isPasswordVisible) {
            // Mostrar a senha no TextField e alterar a imagem para "olho aberto"
            passwordField.setVisible(false);
            visiblePasswordField.setVisible(true);
            togglePasswordVisibility.setImage(new Image("assets/assets/login/eye-hide-line.png")); // Ícone de "olho aberto"
        } else {
            // Ocultar a senha no PasswordField e alterar a imagem para "olho fechado"
            passwordField.setVisible(true);
            visiblePasswordField.setVisible(false);
            togglePasswordVisibility.setImage(new Image("assets/assets/login/eye-line.png")); // Ícone de "olho fechado"
        }
    }

    @FXML
    public void handleLogin() {
        System.out.println("called handleLogin");
        try {
            String cpf = cpfField.getText().trim();
            String password = passwordField.getText();

            verifyIfIsEmpty(cpf, password);

            boolean authenticated = customerService.authenticate(cpf, password);

            System.out.println("Login bem sucedido!");

            if (authenticated) {
                try {
                    FXMLLoader loader = SpringFXMLLoader.create("/main-view.fxml");
                    Parent root = loader.load();

                    // Pegar o stage atual através do loginButton
                    Stage stage = (Stage) loginButton.getScene().getWindow();

                    // Configurar a nova cena
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    // Opcional: configurar título da janela
                    stage.setTitle("Menu Principal");

                    stage.show();

                } catch (Exception e) {
                    System.err.println("Erro ao carregar tela de login: " + e.getMessage());
                    e.printStackTrace();
                }
                System.out.println("Login bem sucedido! 2");
            } else {
                showError("Credenciais inválidas");
            }
        } catch (RuntimeException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void handleForgotPassword() {
        // Método para recuperar senha

        // try {
        //     String email = emailField.getText().trim();
        //     if (email.isEmpty()) {
        //         showError("Digite seu email para recuperar a senha");
        //         return;
        //     }
        //
        //     authenticationService.resetPassword(email);
        //     showMessage();
        // } catch (RuntimeException e) {
        //     showError(e.getMessage());
        // }
    }

    @FXML
    public void handleRegisterNavigation(ActionEvent event) {
        // Usando o ViewSwitcher para alternar para a tela de registro
        ViewSwitcher.switchTo(AccountView.CREATE.getView());
        System.out.println("Navegando para registro...");

        try {
            FXMLLoader loader = SpringFXMLLoader.create("/register-user.fxml");
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void verifyIfIsEmpty(String cpf, String password) {
        if (cpf.isEmpty() || password.isEmpty() || !Validations.validateCPF(cpf)) {
            errorLabel.setVisible(true);
            showError("CPF inválido ou campos vazios.");
        }
    }

    private void showError(String message) {
        System.out.println("Updating error label with message: " + message); // Log para verificar se o método está sendo chamado
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.getStyleClass().remove("success-message");
        errorLabel.getStyleClass().add("error-message");
    }

    private void showMessage() {
        errorLabel.setText("Instruções de recuperação de senha foram enviadas para seu email");
        errorLabel.setVisible(true);
        errorLabel.getStyleClass().remove("error-message");
        errorLabel.getStyleClass().add("success-message");
    }

    @FXML
    public void handleHelp(ActionEvent event) {

    }

    @FXML
    private void showHelpModal(ActionEvent event) {
        try {
            // Carregar o FXML do modal de ajuda
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/help.fxml"));
            Parent root = loader.load();

            // Criar um novo Stage para o modal de ajuda
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            modalStage.setTitle("Help");

            // Configurar e exibir o modal
            Scene scene = new Scene(root);
            scene.setFill(null); // Fundo transparente, se necessário
            modalStage.setScene(scene);
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
