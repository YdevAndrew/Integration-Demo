package org.jala.university.presentation.controller.External;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentReceiptController {

    @FXML
    private Label amountLabel;
    @FXML
    private Label expirationDateLabel;
    @FXML
    private Label receiverNameLabel;
    @FXML
    private Label accountLabel;
    @FXML
    private Label agencyLabel;
    @FXML
    private Label bankReceiverLabel;  // Banco (fixo)
    @FXML
    private Label interestValueLabel;  // Exibe o valor dos juros
    @FXML
    private Label totalValueLabel;    // Exibe o valor total com juros
    @FXML
    private Label userNameLabel;
    @FXML
    private Label userAccountLabel;
    @FXML
    private Label userAgencyLabel;
    @FXML
    private Label userBankLabel;
    @FXML
    private Label transferTypeLabel;
    @FXML
    private Label dateLabel;  // Label para exibir a data e hora atual

    // Método para inicializar os dados no comprovante
    public void initializeReceipt(double amount, String receiverName, String account, String agency, String expirationDate, String interestValue, String totalValue) {
        // Dados do pagamento
        amountLabel.setText("R$ " + String.format("%.2f", amount));
        expirationDateLabel.setText(expirationDate);
        receiverNameLabel.setText(receiverName);
        accountLabel.setText(agency);
        agencyLabel.setText(account);
//        accountLabel.setText(account);
//        agencyLabel.setText(agency);

        // Banco fixo
        bankReceiverLabel.setText("Instituição Externa");

        // Exibindo os valores de juros e total
        interestValueLabel.setText(interestValue);  // Exibe o valor de juros recebido
        totalValueLabel.setText(totalValue);        // Exibe o valor total com juros

        transferTypeLabel.setText("Manual Payment");

        // Dados do usuário
        userNameLabel.setText("John Due");
        userAccountLabel.setText("9456326-6");
        userAgencyLabel.setText("4545");
        userBankLabel.setText("JalaU Bank");

        // Adicionar a data e hora atual
        String currentDateTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        dateLabel.setText(currentDateTime);
    }

    // Método para lidar com o botão "OK" - assim que encerrar abre uma nova página do dashboard
    @FXML
    private void onOkButtonClick() {
        // Obtém a janela (stage) atual, que é o pop-up
        Stage currentStage = (Stage) amountLabel.getScene().getWindow();

        // Fecha o pop-up
        currentStage.close();

        // Agora, vamos carregar a tela do DashboardController (tela inicial)
        try {
            // Carrega o FXML do DashboardController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/board/DashboardApp.fxml"));
            Parent root = loader.load();

            // Verifica se a janela principal (DashboardController) já está aberta
            Stage mainStage = (Stage) currentStage.getOwner(); // Tenta pegar a janela que originou o pop-up

            if (mainStage == null) {
                // Se não existir, cria uma nova janela principal
                mainStage = new Stage();
            }

            // Define o título e a cena da janela principal
            mainStage.setTitle("External Payment Module Application");
            mainStage.setScene(new Scene(root));

            // Exibe a janela com o DashboardController
            mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Em caso de erro ao carregar o DashboardController, imprime uma mensagem de erro
            System.out.println("Erro ao carregar o DashboardController.");
        }
    }

    // Método para lidar com o botão "Exportar Comprovante"
    @FXML
    private void onExportButtonClick() {
        // Implementar exportação, como salvar o comprovante em um arquivo ou gerar PDF
        System.out.println("Exportando comprovante...");
    }
}
