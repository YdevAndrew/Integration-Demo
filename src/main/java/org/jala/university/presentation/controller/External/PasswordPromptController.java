package org.jala.university.presentation.controller.External;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jala.university.application.dto.dto_transaction.PaymentHistoryDTO;
import org.jala.university.application.service.service_transaction.PaymentHistoryService;
import org.jala.university.application.service.service_transaction.PaymentHistoryServiceImpl;
import org.jala.university.commons.presentation.BaseController;
import org.jala.university.presentation.controller.Loan.SpringFXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Consumer;

import java.util.function.Consumer;
import java.io.IOException;

@Controller
public class PasswordPromptController extends BaseController {
    @Autowired
    SpringFXMLLoader springFXMLLoader;
    @Autowired
    private PaymentHistoryService paymentHistoryService;
    @FXML
    private PasswordField passwordField;
    private Consumer<String> passwordHandler;
    private String path;
    public BigDecimal amount;
    public String receiverName;
    public String agency;
    public String account;
    public LocalDate expirationDate;
    public String cnpjReceiver;

    public void setDetails(BigDecimal amount, String receiverName, String agency, String account, String expirationDate, String cnpjReceiver) {
        this.amount = (amount);
        this.receiverName = receiverName;
        this.agency = agency;
        this.account = account;
        this.expirationDate = LocalDate.parse(expirationDate);
        this.cnpjReceiver = cnpjReceiver;
    }

     /* Define o callback para manipular a senha inserida.
     */
    public void setPasswordHandler(Consumer<String> handler) {
        this.passwordHandler = handler;
    }

    public void setPath(String path){
        this.path = path;
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
                PaymentHistoryDTO paymentHistoryDTO = PaymentHistoryDTO.builder()
                        .amount(amount)
                        .nameReceiver(receiverName)
                        .agencyReceiver(agency)
                        .accountReceiver(account)
                        .expiredDate(expirationDate)
                        .cpfReceiver(cnpjReceiver).build()
                        ;
                paymentHistoryService.createExternalPayment(48, paymentHistoryDTO,"EXTERNAL_PAYMENT");

                System.out.println("Path");
                System.out.println(path);
                FXMLLoader fxmlLoader = springFXMLLoader.load(path);
                Pane root = fxmlLoader.load();

                Stage paymentStatusStage = new Stage();
                paymentStatusStage.setTitle("Status payment");
                paymentStatusStage.setScene(new Scene(root));
                paymentStatusStage.show();


                // Fecha a janela atual após abrir a nova
                Stage currentStage = (Stage) passwordField.getScene().getWindow();
                currentStage.close(); // Fecha o pop-up de senha após sucesso

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading the payment status screen.", ButtonType.OK);
                alert.showAndWait();
                e.printStackTrace();
            }

            paymentDetailsController.showPaymentReceipt();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect password. Please try again.", ButtonType.OK);
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

    public void setScreenConfirmAndDeleteController(ScreenConfirmAndDeleteController screenConfirmAndDeleteController) {
    }
}