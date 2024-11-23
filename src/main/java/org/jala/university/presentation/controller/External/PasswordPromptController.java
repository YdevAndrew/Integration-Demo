package org.jala.university.presentation.controller.External;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class PasswordPromptController {

    @FXML
    private PasswordField passwordField;

    // Defina a senha correta (exemplo: "1234")
    private static final String CORRECT_PASSWORD = "1234";

    private ManualPaymentInformationController paymentDetailsController;
    private SchedulePaymentInformationController schedulePaymentInformationController;

    // Método que será chamado quando o pop-up for exibido
    public void setPaymentDetailsController(ManualPaymentInformationController paymentDetailsController) {
        this.paymentDetailsController = paymentDetailsController;
    }

    // Método que será chamado quando o pop-up for exibido
    public void setSchedulePaymentInformationController(SchedulePaymentInformationController schedulePaymentInformationController) {
        SchedulePaymentInformationController setschedulePaymentInformationController = null;
        this.schedulePaymentInformationController = setschedulePaymentInformationController;
    }

    // Método chamado quando o usuário clica no botão "Confirmar"
    @FXML
    private void onConfirmButtonClick() {
        String enteredPassword = passwordField.getText();

        // Verifique se a senha está correta
        if (CORRECT_PASSWORD.equals(enteredPassword)) {
            // Fechar o pop-up de senha
            Stage stage = (Stage) passwordField.getScene().getWindow();
            stage.close();

            // Agora que a senha foi validada, abra o comprovante de pagamento
            paymentDetailsController.showPaymentReceipt();
        } else {
            // Exibir alerta de erro se a senha estiver errada
            Alert alert = new Alert(Alert.AlertType.ERROR, "Senha incorreta. Tente novamente.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    // Método chamado quando o usuário clica no botão "Cancelar"
    @FXML
    private void onCancelButtonClick() {
        // Fecha o pop-up
        Stage stage = (Stage) passwordField.getScene().getWindow();
        stage.close();
    }
}
