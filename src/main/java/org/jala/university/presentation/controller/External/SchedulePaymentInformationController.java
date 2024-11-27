package org.jala.university.presentation.controller.External;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jala.university.commons.presentation.BaseController;
import org.jala.university.presentation.controller.Loan.SpringFXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class SchedulePaymentInformationController extends BaseController {
    @Autowired
    SpringFXMLLoader springFXMLLoader;

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


    private BigDecimal amount;
    private String receiverName;
    private String agency;
    private String account;
    private String expirationDate;
    private String cnpjReceiver;
    public LocalDate startDate;
    public LocalDate endDate;
    public String description;
    public LocalDateTime transactionDate;


    // Método para definir os dados do pagamento com validações
    public void setPaymentData(BigDecimal amount, String recipient,
                               String agency, String account, String dueDate, LocalDateTime transactionDate, String description,
                               LocalDate startDate, LocalDate endDate, boolean isCNPJ) {

        serviceNameText.setText(description);
        recipientText.setText(recipient);
        serviceAmountText.setText(String.valueOf(amount));
        agencyText.setText(agency);
        accountText.setText(account);
        dueDateText.setText(dueDate);
        startDateText.setText(String.valueOf(startDate));
        this.amount = amount;
        this.cnpjReceiver = recipient;
        this.agency = agency;
        this.account = account;
        this.expirationDate = dueDate;
        this.transactionDate = transactionDate;
        this.startDate = startDate;
        this.description = description;

        if (endDate != null) {
            try {

                // Formatar para exibir apenas mês e ano
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                String formattedDate = endDate.format(formatter);

                // Exibir o resultado formatado
                endDateText.setText(formattedDate);
                this.endDate = LocalDate.parse(formattedDate, formatter);
            } catch (Exception e) {
                endDateText.setText("Invalid Date");
            }

        } else {
            endDateText.setText("Indefinite");
            this.endDate = null;
        }

        // Realiza validações baseadas em CNPJ ou CPF
        if (isCNPJ) {
            recipientDocumentText.setText("CNPJ: ");
            recipientNameText.setText("External User");
            bankNameText.setText("External Bank");

            this.receiverName = "External User";

        } else {
            recipientDocumentText.setText("CPF: ");
            if ("4545".equals(agency)) {
                recipientNameText.setText("Bank User");
                bankNameText.setText("JalaU");
                this.receiverName = "Bank User";

            } else {
                recipientNameText.setText("External User");
                bankNameText.setText("External Bank");
                this.receiverName = "External User";

            }
        }
    }

    @FXML
    private void back() throws IOException {
        FXMLLoader loader = springFXMLLoader.load("/External/SchedulePaymentScreens/SchedulePayment/SchedulePayment.fxml");
        Pane schedulePayment = loader.load();
        mainContent.getChildren().setAll(schedulePayment);
    }

    // Método chamado quando o usuário clica no botão "Confirmar"
    @FXML
    private void insertPassword() {
        try {
            // Carrega o FXML do pop-up de senha
            FXMLLoader loader = springFXMLLoader.load("/External/password/PasswordPrompt.fxml");
            Parent root = loader.load();

            // Inicializa o controlador do pop-up de senha
            PasswordPromptController passwordPromptController = loader.getController();
            passwordPromptController.setDetails(amount,receiverName,account,agency,expirationDate,cnpjReceiver,transactionDate,description,startDate,endDate);
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