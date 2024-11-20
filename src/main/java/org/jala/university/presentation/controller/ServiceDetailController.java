package org.jala.university.presentation.controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class ServiceDetailController {
    @FXML
    private AnchorPane mainContent;
    @FXML
    public void handleEditClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ScheduleServices/EditService.fxml"));
        Pane serviceDetailContent = loader.load();
        mainContent.getChildren().setAll(serviceDetailContent);
    }
    @FXML
    public void screenDeleteClick(ActionEvent event) {
        try {
            // Carregar o FXML da tela de confirmação de exclusão
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ScheduleServices/ScreenDelete.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ScheduleServices/ScreenDeleteSuccess.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ScheduleServices/ButtonService.fxml")); // Caminho para a tela anterior
            Pane previousScreenContent = loader.load();

            // Substitui o conteúdo da tela atual pelo da tela anterior
            mainContent.getChildren().setAll(previousScreenContent);

        } catch (IOException e) {
            e.printStackTrace(); // Tratar erros de carregamento da tela
        }
    }

}