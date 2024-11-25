package org.jala.university.presentation.controller.External;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jala.university.commons.presentation.BaseController;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class SchedulePaymentInformationController extends BaseController {

    @FXML
    private Text serviceNameText;
    @FXML
    private Text recipientNameText;
    @FXML
    private Text recipientDocumentText;
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
    public void setPaymentData(String serviceName, String recipient, BigDecimal amount,
                               String agency, String account, String dueDate,
                               String startDate, String endDate, boolean isCNPJ) {

        serviceNameText.setText(serviceName);
        recipientText.setText(recipient);
        serviceAmountText.setText(String.valueOf(amount));
        agencyText.setText(agency);
        accountText.setText(account);
        dueDateText.setText(dueDate);
        startDateText.setText(startDate);

        if (endDate.trim().isEmpty()) {
            endDateText.setText("Indefinite");
        } else {
            try {
                // Converter para LocalDate
                LocalDate date = LocalDate.parse(endDate);

                // Formatar para exibir apenas mês e ano
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                String formattedDate = date.format(formatter);

                // Exibir o resultado formatado
                endDateText.setText(formattedDate);
            } catch (Exception e) {
                endDateText.setText("Invalid Date");
            }
        }

        // Realiza validações baseadas em CNPJ ou CPF
        if (isCNPJ) {
            recipientDocumentText.setText("CNPJ: ");
            recipientNameText.setText("External User");
            bankNameText.setText("External Bank");
        } else {
            recipientDocumentText.setText("CPF: ");
            if ("4545".equals(agency)) {
                recipientNameText.setText("Bank User");
                bankNameText.setText("JalaU");
            } else {
                recipientNameText.setText("External User");
                bankNameText.setText("External Bank");
            }
        }
    }

    @FXML
    private void back() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/SchedulePaymentScreens/SchedulePayment/SchedulePayment.fxml"));
        Pane schedulePayment = loader.load();
        mainContent.getChildren().setAll(schedulePayment);
    }

    // Método chamado quando o usuário clica no botão "Confirmar"
    @FXML
    private void insertPassword() {
        try {
            // Carrega o FXML do pop-up de senha
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/password/PasswordPrompt.fxml"));
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