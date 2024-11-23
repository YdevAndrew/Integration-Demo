package org.jala.university.presentation.controller.External;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class SchedulePaymentInformationController {

    @FXML
    private Text serviceDescriptionText;
    @FXML
    private Text recipientText;
    @FXML
    private Text agencyText;
    @FXML
    private Text accountText;
    @FXML
    private Text serviceAmountText;
    @FXML
    private Text dueDateText;
    @FXML
    private Text startDateText;
    @FXML
    private Text endDateText;
    @FXML
    private Text bankNameText;
    @FXML
    private Pane mainContent;

    // Método para definir os dados do pagamento com validações
    public void setPaymentData(String serviceDescription, String recipient, String amount,
                               String agency, String account, String dueDate,
                               String startDate, String endDate, boolean isCNPJ) {

        serviceDescriptionText.setText(serviceDescription);
        serviceAmountText.setText(amount);
        agencyText.setText(agency);
        accountText.setText(account);
        dueDateText.setText(dueDate);
        startDateText.setText(startDate);
        endDateText.setText(endDate);

        // Realiza validações baseadas em CNPJ ou CPF
        if (isCNPJ) {
            recipientText.setText("External User");
            bankNameText.setText("External Bank");
        } else {
            if ("4545".equals(agency)) {
                recipientText.setText("Bank User");
                bankNameText.setText("JalaU");
            } else {
                recipientText.setText("External User");
                bankNameText.setText("External Bank");
            }
        }
    }

    @FXML
    private void back() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/gui/SchedulePaymentScreens/SchedulePayment/SchedulePayment.fxml"));
        Pane schedulePayment = loader.load();
        mainContent.getChildren().setAll(schedulePayment);
    }

    // Método chamado quando o usuário clica no botão "Confirmar"
    @FXML
    private void insertPassword() {
        try {
            // Carrega o FXML do pop-up de senha
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/gui/password/PasswordPrompt.fxml"));
            Parent root = loader.load();

            // Inicializa o controlador do pop-up de senha
            PasswordPromptController passwordPromptController = loader.getController();
            if (passwordPromptController != null) {
                // Passa o controlador de SchedulePaymentInformationController para o pop-up de senha
                passwordPromptController.setSchedulePaymentInformationController(this);  // Altere para passar o controlador correto
            } else {
                System.out.println("Erro: O controlador PasswordPromptController não foi injetado corretamente.");
            }

            // Exibe o pop-up de senha
            Stage stage = new Stage();
            stage.setTitle("Autenticação");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Caso ocorra algum erro, você pode tratar isso aqui
        }
    }
}
