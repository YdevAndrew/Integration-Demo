package org.jala.university.presentation.controller.External;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jala.university.commons.presentation.BaseController;
import org.jala.university.presentation.controller.Loan.SpringFXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PaymentReceiptController extends BaseController {
    @Autowired
    private SpringFXMLLoader springFXMLLoader;


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
    private Label bankReceiverLabel;
    @FXML
    private Label interestValueLabel;
    @FXML
    private Label totalValueLabel;
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
    private Label dateLabel;


    //    //     Método para exportar como PDF (imagem ok)
    @FXML
    private void exportAsPDF() {
        try {
            // Seleção do local para salvar o PDF
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                // Garantir que o arquivo termine com .pdf
                if (!file.getName().toLowerCase().endsWith(".pdf")) {
                    file = new File(file.getAbsolutePath() + ".pdf");
                }

                // Criação do documento PDF
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page); // Adicionando página ao documento

                // Carregar a imagem (logo) do classpath
                PDImageXObject pdImage = null;
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("img/jala_bank_logo.jpg");

                if (inputStream != null) {
                    // Se a imagem for encontrada no classpath, criamos um PDImageXObject a partir do InputStream
                    byte[] imageBytes = inputStream.readAllBytes();  // Lê todos os bytes da imagem
                    pdImage = PDImageXObject.createFromByteArray(document, imageBytes, "jala_bank_logo.jpg");
                } else {
                    System.out.println("Image not found on classpath.");
                }

                // Iniciar o fluxo de conteúdo no PDF
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Definindo fontes para título e conteúdo
                PDFont titleFont = PDType1Font.HELVETICA_BOLD;
                PDFont bodyFont = PDType1Font.HELVETICA;

                // Adicionar a imagem no canto superior direito se a imagem foi carregada
                if (pdImage != null) {
                    // Definir o tamanho da imagem (ajustar conforme necessário)
                    float imageWidth = 100; // Largura da imagem
                    float imageHeight = pdImage.getHeight() * (imageWidth / pdImage.getWidth()); // Mantendo a proporção

                    // Posição da imagem no canto superior direito
                    float imageX = page.getMediaBox().getWidth() - imageWidth - 50; // Posição horizontal (canto direito)
                    float imageY = page.getMediaBox().getHeight() - imageHeight - 50; // Posição vertical (canto superior)

                    contentStream.drawImage(pdImage, imageX, imageY, imageWidth, imageHeight);
                }


                // Definir posição inicial no PDF
                contentStream.setFont(titleFont, 36);
                contentStream.beginText();
                contentStream.setNonStrokingColor(new Color(0, 75, 214)); // Azul
                contentStream.newLineAtOffset(50, 750);

                // Título do PDF
                contentStream.showText("Transfer Receipt");
                contentStream.newLineAtOffset(0, -50); // Espaçamento abaixo do título

                // Informações do pagamento
                contentStream.setFont(bodyFont, 18);
                contentStream.showText("Date: " + dateLabel.getText());
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("Receiver: " + receiverNameLabel.getText());
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("Amount: " + amountLabel.getText());
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("Interest: " + interestValueLabel.getText());
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("Total: " + totalValueLabel.getText());
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("Bank: " + bankReceiverLabel.getText());

                contentStream.newLineAtOffset(0, -50); // Espaçamento extra

                // Informações do usuário
                contentStream.showText("User Name: " + userNameLabel.getText());
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("User Account: " + userAccountLabel.getText());
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("User Agency: " + userAgencyLabel.getText());
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("User Bank: " + userBankLabel.getText());

                contentStream.newLineAtOffset(0, -50); // Espaçamento extra

                // Tipo de transferência
                contentStream.showText("Transfer Type: " + transferTypeLabel.getText());

                // Finalizar e fechar o fluxo de texto
                contentStream.endText();
                contentStream.close();

                // Salvar o documento PDF no arquivo
                document.save(file);
                document.close();

                System.out.println("PDF exported successfully");
            } else {
                System.out.println("Nenhum arquivo foi selecionado ou a operação foi cancelada.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error when trying to save the PDF: " + e.getMessage());
        }
    }


    // Método de inicialização do controlador
    public void initializeReceipt(double amount, String receiverName, String account, String agency, String expirationDate, String interestValue, String totalValue) {
        // Definir os valores nos labels
        amountLabel.setText("R$ " + String.format("%.2f", amount));
        expirationDateLabel.setText(expirationDate);
        receiverNameLabel.setText(receiverName);
        accountLabel.setText(agency);
        agencyLabel.setText(account);
        bankReceiverLabel.setText("Instituição Externa");
        interestValueLabel.setText(interestValue);
        totalValueLabel.setText(totalValue);
        transferTypeLabel.setText("Manual Payment");
        userNameLabel.setText("John Due");
        userAccountLabel.setText("9456326-6");
        userAgencyLabel.setText("4545");
        userBankLabel.setText("JalaU Bank");

        // Data e hora atual
        String currentDateTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        dateLabel.setText(currentDateTime);
    }


    @FXML
    private void onExportButtonClick() {
        // Mostrar opções para o usuário
        System.out.println("Choose the format to export");
        // Aqui você pode criar uma janela para o usuário escolher o formato (imagem ou PDF)
        exportAsPDF();
    }

    // Método para lidar com o botão "OK" - assim que encerrar abre uma nova página do dashboard
    @FXML
    private void onOkButtonClick() {
        // Obtém a janela (stage) atual, que é o pop-up
        Stage currentStage = (Stage) amountLabel.getScene().getWindow();

        // Fecha o pop-up
        currentStage.close();

        // Agora, vamos carregar a tela do Dashboard (tela inicial)
        try {
            // Carrega o FXML do Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/ManualPaymentScreens/QRCodePayment/QRCodePayment.fxml"));
            Parent root = loader.load();

            // Verifica se a janela principal (Dashboard) já está aberta
            Stage mainStage = (Stage) currentStage.getOwner(); // Tenta pegar a janela que originou o pop-up

            if (mainStage == null) {
                // Se não existir, cria uma nova janela principal
                mainStage = new Stage();
            }

            // Define o título e a cena da janela principal
            mainStage.setTitle("External Payment Module Application");
            mainStage.setScene(new Scene(root));

            // Exibe a janela com o Dashboard
            mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Em caso de erro ao carregar o Dashboard, imprime uma mensagem de erro
            System.out.println("Error loading Dashboard.");
        }
    }

}




