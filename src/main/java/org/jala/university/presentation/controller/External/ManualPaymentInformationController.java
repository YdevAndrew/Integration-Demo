package org.jala.university.presentation.controller.External;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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
import java.time.temporal.ChronoUnit;

@Controller
public class ManualPaymentInformationController extends BaseController {
    @Autowired
    SpringFXMLLoader springFXMLLoader;
    @FXML
    private Label amountLabel;
    @FXML
    private Label receiverNameLabel;
    @FXML
    private Label agencyLabel;
    @FXML
    private Label accountLabel;
    @FXML
    private Label expirationDateLabel;
    @FXML
    private Label cnpjReceiverLabel;
    @FXML
    private Label bankReceiverLabel;
    @FXML
    private Label interestValueLabel;
    @FXML
    private Label totalValueLabel;
    @FXML
    private Label statusLabel;  // Declarando o statusLabel

    @FXML
    private Button manualEntryButton;  // Declara o botão para que ele seja injetado pelo FXML

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Pane mainContent;

    private BigDecimal amount;
    private String receiverName;
    private String agency;
    private String account;
    private LocalDateTime transactionDate;
    private String expirationDate;
    private String cnpjReceiver;
    public LocalDate startDate;
    public LocalDate endDate;
    public String description;

    private QRCodePaymentController qrCodePaymentController;  // Referência para o controlador da tela anterior
    private ManuallyInsertController manuallyInsertController;

    public void setQRCodePaymentController(QRCodePaymentController qrCodePaymentController) {
        this.qrCodePaymentController = qrCodePaymentController;
    }
    public void setManuallyInsertController(ManuallyInsertController manuallyInsertController) {
        this.manuallyInsertController = manuallyInsertController;
    }

    private static final BigDecimal JUROS_DIA = BigDecimal.valueOf(0.01); // 1% ao dia

    // Este método será chamado para inicializar os dados extraídos
    public void initializePaymentDetails(BigDecimal amount, String receiverName, String account, String agency, String expirationDate, String cnpjReceiver, LocalDateTime transactionDate, String description, LocalDate startDate, LocalDate endDate) {
        this.amount = amount;
        this.receiverName = receiverName;
        this.transactionDate = transactionDate;
        this.account = account;
        this.agency = agency;
        this.expirationDate = expirationDate;
        this.cnpjReceiver = cnpjReceiver;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;

        // Atualiza os rótulos com os dados para exibição
        amountLabel.setText("R$ " + amount);
        receiverNameLabel.setText(receiverName);
        accountLabel.setText(agency);  // Aqui você inverte as variáveis
        agencyLabel.setText(account);  // E aqui também
        expirationDateLabel.setText(expirationDate);
        cnpjReceiverLabel.setText(cnpjReceiver);
        bankReceiverLabel.setText("External Bank");

        // Verifica se a data de vencimento passou e calcula os juros
        calcularJurosSeVencido(expirationDate);
    }


    // Método para calcular juros se a data de vencimento já passou
    public void calcularJurosSeVencido(String expirationDate) {
        // Formato da data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Obtém as datas
        LocalDate dataVencimento = LocalDate.parse(expirationDate, formatter);
        LocalDate dataAtual = LocalDate.now();

        // Verifica se a data de vencimento já passou
        if (dataVencimento.isBefore(dataAtual)) {
            // Calcula a diferença de dias entre a data atual e a data de vencimento
            long diasAtraso = ChronoUnit.DAYS.between(dataVencimento, dataAtual);
            BigDecimal juros = JUROS_DIA.multiply(new BigDecimal(diasAtraso)).multiply(amount); // Juros simples
            BigDecimal total = amount.add(juros);

            // Atualiza os campos de juros e total na interface
            interestValueLabel.setText("R$ " + juros.setScale(2, BigDecimal.ROUND_HALF_UP));
            totalValueLabel.setText("R$ " + total.setScale(2, BigDecimal.ROUND_HALF_UP));
        } else {
            // Caso a data de vencimento ainda não tenha passado
            interestValueLabel.setText("R$ 0.00");
            totalValueLabel.setText("R$ " + amount.setScale(2, BigDecimal.ROUND_HALF_UP));;
        }
    }


    // Método chamado quando o usuário clica no botão "Confirmar"
    @FXML
    private void onConfirmButtonClick() {
        try {
            // Carrega o FXML do pop-up de senha
            FXMLLoader loader = springFXMLLoader.load("/External/password/PasswordPrompt.fxml");
            Parent root = loader.load();

            // Inicializa o controlador do pop-up de senha
            PasswordPromptController passwordPromptController = loader.getController();
            passwordPromptController.setDetails(amount,receiverName,account,agency,expirationDate,cnpjReceiver,transactionDate,description,startDate,endDate);
            if (passwordPromptController != null) {
                // Define a tela que irá aparecer após a confirmação da senha
                passwordPromptController.setPath("/External/ScheduleServices/PaymentStatus.fxml");
                // Passa o controlador de PaymentDetailsController para o pop-up de senha
                passwordPromptController.setPaymentDetailsController(this);

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
            if (statusLabel != null) {
                statusLabel.setText("Erro ao carregar o pop-up de senha.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }


    // Método para exibir o comprovante de pagamento
    public void showPaymentReceipt() {
        try {
            // Carrega a tela de comprovante de pagamento
            FXMLLoader loader = springFXMLLoader.load("/External/PaymentReceipt/PaymentReceipt.fxml");
            Pane paymentReceiptPane = loader.load();

            // Inicializa o controlador da tela de comprovante
            PaymentReceiptController paymentReceiptController = loader.getController();
            if (paymentReceiptController != null) {
                // Passa os dados da primeira tela para a segunda
                paymentReceiptController.initializeReceipt(amount, receiverName, account, agency, expirationDate, interestValueLabel.getText(), totalValueLabel.getText());
            } else {
                System.out.println("Erro: O controlador PaymentReceiptController não foi injetado corretamente.");
            }
            // Substitui o conteúdo atual da tela com o painel de detalhes de pagamento
            mainContent.getChildren().setAll(paymentReceiptPane);

            // Exibe o comprovante em uma nova janela
            Stage stage = new Stage();
            stage.setTitle("Comprovante de Pagamento");
            stage.setScene(new Scene(paymentReceiptPane));
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
            if (statusLabel != null) {
                statusLabel.setText("Erro ao carregar o comprovante.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }


    // Método chamado quando o usuário clica no botão "Cancelar"
    @FXML
    private void onCancelButtonClick() {
        // Fecha a janela atual (PaymentDetails)
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

        // Verifica se o controlador anterior foi passado e altera a visibilidade do botão
        if (qrCodePaymentController != null) {
            qrCodePaymentController.showManualEntryButton();  // Torna o botão visível na tela anterior
        }
    }



    @FXML
    private void back() throws IOException {
        FXMLLoader loader = springFXMLLoader.load("/External/ManualPaymentScreens/QRCodePayment/QRCodePayment.fxml");
        Pane schedulePayment = loader.load();
        mainContent.getChildren().setAll(schedulePayment);
    }


}



