package org.jala.university.presentation.controllerCreditCard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.jala.university.requestCard.tools.ConfigurationsCard;

import java.io.IOException;

public class DeleteVirtualCardController {

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private CheckBox showPasswordCheckBox;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private TextField repeatPasswordTextField;
    @FXML
    private CheckBox showRepeatPasswordCheckBox;
    @FXML
    private Button confirmButton;
    @FXML
    private Label repeatPasswordErrorLabel;

    @FXML
    private void initialize() {
        System.out.println(CardsPageController.getCardClicked());
        // Configuração para passwordField
        passwordTextField.managedProperty().bind(showPasswordCheckBox.selectedProperty());
        passwordTextField.visibleProperty().bind(showPasswordCheckBox.selectedProperty());
        passwordField.managedProperty().bind(showPasswordCheckBox.selectedProperty().not());
        passwordField.visibleProperty().bind(showPasswordCheckBox.selectedProperty().not());
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());

        // Configuração para repeatPasswordField
        repeatPasswordTextField.managedProperty().bind(showRepeatPasswordCheckBox.selectedProperty());
        repeatPasswordTextField.visibleProperty().bind(showRepeatPasswordCheckBox.selectedProperty());
        repeatPasswordField.managedProperty().bind(showRepeatPasswordCheckBox.selectedProperty().not());
        repeatPasswordField.visibleProperty().bind(showRepeatPasswordCheckBox.selectedProperty().not());
        repeatPasswordTextField.textProperty().bindBidirectional(repeatPasswordField.textProperty());

        confirmButton.setOnAction(event -> {
            try {
                handleConfirmButtonAction();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void handleConfirmButtonAction() throws IOException {
        String password = passwordField.getText();
        String repeatPassword = repeatPasswordField.getText();

        if (password.equals(repeatPassword)) {
            if(ConfigurationsCard.deleterCard(CardsPageController.getCardClicked(), password, repeatPasswordErrorLabel)){
                showAlert("Card deleted successfully!");
                passwordField.clear();
                repeatPasswordField.clear();
                repeatPasswordErrorLabel.setVisible(false);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/creditCardFXMLs/requestAVirtualCard/request_a_virtual_card.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.setScene(new Scene(root));
            }
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}