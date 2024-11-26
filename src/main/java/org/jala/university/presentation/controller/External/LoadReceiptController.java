package org.jala.university.presentation.controller.External;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LoadReceiptController {

    @FXML
    private Button okButton;

    @FXML
    private void initialize() {
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> showPaymentReceipt());
        pause.play();
    }

    @FXML
    private void onOkButtonClick() {
        showPaymentReceipt();
    }

    private void showPaymentReceipt() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/PaymentReceipt/PaymentReceipt.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Payment Receipt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setManualPaymentInformationController(PasswordPromptController passwordPromptController) {
    }
}
