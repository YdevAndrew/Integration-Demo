package org.jala.university.presentation.controller.External;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.function.Consumer;
import java.io.IOException;
public class PaymentSchedulePASSWORDController {

    @FXML
    private PasswordField passwordField;
    private Consumer<String> passwordHandler;

    /**
     * Define o callback para manipular a senha inserida.
     */
    public void setPasswordHandler(Consumer<String> handler) {
        this.passwordHandler = handler;
    }

    private static final String CORRECT_PASSWORD = "1234";

    private ManualPaymentInformationController paymentDetailsController;
    private Runnable onPasswordVerified;// Callback para lógica personalizada após senha correta

    public void setPaymentDetailsController(ManualPaymentInformationController paymentDetailsController) {
        this.paymentDetailsController = paymentDetailsController;
    }

    public void setSchedulePaymentInformationController(SchedulePaymentInformationController schedulePaymentInformationController) {
    }


    @FXML
    private void onConfirmButtonClick() {
        String enteredPassword = passwordField.getText();

        if (CORRECT_PASSWORD.equals(enteredPassword)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/External/ScheduleServices/paymentScheduleSucess.fxml"));
                Pane root = fxmlLoader.load();


                Stage paymentStatusStage = new Stage();
                paymentStatusStage.setTitle("Payment Status");
                paymentStatusStage.setScene(new Scene(root));
                paymentStatusStage.show();


                // Fecha a janela atual após abrir a nova
                Stage currentStage = (Stage) passwordField.getScene().getWindow();
                currentStage.close(); // Fecha o pop-up de senha após sucesso
                // Exibe o recibo de pagamento
                if (paymentDetailsController != null) {
                    paymentDetailsController.showPaymentReceipt();
                }

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading screen Payment Status.", ButtonType.OK);
                alert.showAndWait();
                e.printStackTrace();
            }

            paymentDetailsController.showPaymentReceipt();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect password. Try again.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void onCancelButtonClick() {
        Stage stage = (Stage) passwordField.getScene().getWindow();
        stage.close();
    }

    public void setOnPasswordVerified(Runnable onPasswordVerified) {

    }

    public void setPasswordHandler(Object o) {
    }
}



