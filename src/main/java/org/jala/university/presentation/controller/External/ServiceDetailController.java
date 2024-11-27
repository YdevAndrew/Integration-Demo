package org.jala.university.presentation.controller.External;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jala.university.commons.presentation.BaseController;
import org.jala.university.presentation.controller.Loan.SpringFXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class ServiceDetailController extends BaseController {
    @Autowired
    private SpringFXMLLoader springFXMLLoader;


    private String serviceName;
    private String recipientName;
    private String recipientDocument;
    private Text recipient;
    private String agency;
    private String account;
    private String serviceAmount;
    private String dueDate;
    private String startDate;
    private String endDate;
    private Text bankName;

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
    public void initialize(String serviceName, String recipient, String amount,
                               String agency, String account, String dueDate,
                               String startDate, String endDate) {

        this.serviceName = serviceName;
        this.serviceAmount = amount;
        this.account = account;
        this.agency = agency;
        this.dueDate = dueDate;
        this.recipientDocument = recipient;
        this.startDate = startDate;
        this.endDate = endDate;

        serviceNameText.setText("serviceName");
        recipientText.setText("123.536.136/153-25");
        serviceAmountText.setText("R$ " + "450,00");
        agencyText.setText("1252");
        accountText.setText("01010101-0");
        dueDateText.setText("03/11/2024");
        startDateText.setText("startDate");
        recipientNameText.setText("External User");
        bankNameText.setText("External Bank");
        endDateText.setText("Indefinite");
    }


    @FXML
    public void handleEditClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/ScheduleServices/EditService.fxml"));
        Pane serviceDetailContent = loader.load();
        mainContent.getChildren().setAll(serviceDetailContent);
    }
    @FXML
    public void screenDeleteClick(ActionEvent event) {
        try {
            // Carregar o FXML da tela de confirmação de exclusão
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/ScheduleServices/ScreenDelete.fxml"));
            Pane deleteConfirmationPane = loader.load();

            // Criar um novo Stage para  confirmação de exclusão
            Stage deleteStage = new Stage();
            deleteStage.initModality(Modality.APPLICATION_MODAL); // Modal para bloquear a janela principal
            deleteStage.initStyle(StageStyle.UNDECORATED); // Sem bordas
            deleteStage.setTitle("Confirmar Exclusão");

            // Configurar o conteúdo e o tamanho da janela
            Scene scene = new Scene(deleteConfirmationPane);
            deleteStage.setScene(scene);
            deleteStage.setWidth(400);
            deleteStage.setHeight(200);

            // Centralizar o Stage em relação à janela principal
            deleteStage.setX(mainContent.getScene().getWindow().getX() + (mainContent.getScene().getWindow().getWidth() - deleteStage.getWidth()) / 2);
            deleteStage.setY(mainContent.getScene().getWindow().getY() + (mainContent.getScene().getWindow().getHeight() - deleteStage.getHeight()) / 2);

            // Exibir a janela de diálogo
            deleteStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleConfirm(ActionEvent event) {
        // Lógica para excluir o serviço
        System.out.println("Serviço excluído com sucesso!");

        // Após a exclusão ser confirmada, abrir a tela de sucesso
        try {
            // Carregar o FXML da tela de sucesso
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/ScheduleServices/ScreenDeleteSuccess.fxml"));
            Pane successPane = loader.load();

            // Criar um novo Stage para o diálogo de sucesso
            Stage successStage = new Stage();
            successStage.setTitle("Sucesso");

            // Configurar o conteúdo e o tamanho da janela
            Scene scene = new Scene(successPane);
            successStage.setScene(scene);
            successStage.setWidth(400);
            successStage.setHeight(200);

            // Centralizar o Stage em relação à janela principal
            successStage.setX(mainContent.getScene().getWindow().getX() + (mainContent.getScene().getWindow().getWidth() - successStage.getWidth()) / 2);
            successStage.setY(mainContent.getScene().getWindow().getY() + (mainContent.getScene().getWindow().getHeight() - successStage.getHeight()) / 2);

            // Exibir a janela de sucesso
            successStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Fechar a janela de confirmação de exclusão
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    public void handleBackClick(ActionEvent event) {
        // Carregar a tela anterior no mesmo AnchorPane
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/ScheduleServices/ButtonService.fxml")); // Caminho para a tela anterior
            Pane previousScreenContent = loader.load();

            // Substitui o conteúdo da tela atual pelo da tela anterior
            mainContent.getChildren().setAll(previousScreenContent);

        } catch (IOException e) {
            e.printStackTrace(); // Tratar erros de carregamento da tela
        }
    }

}
