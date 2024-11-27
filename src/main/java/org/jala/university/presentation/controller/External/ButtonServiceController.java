package org.jala.university.presentation.controller.External;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ButtonServiceController {

    @FXML
    private AnchorPane mainContent; // Painel principal onde o conteúdo será carregado

    /**
     * Método para carregar uma nova tela dentro do `mainContent`.
     * Substitui o conteúdo existente pelo novo.
     *
     */
    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Pane newContent = loader.load();
            mainContent.getChildren().setAll(newContent); // Substitui o conteúdo atual pelo novo
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar a tela: " + fxmlPath);
        }
    }


    @FXML
    public void handleService1Click(ActionEvent event) {
        loadContent("/External/ScheduleServices/ServiceDetail.fxml");
    }

    @FXML
    public void handleService2Click(ActionEvent event) {
        loadContent("/External/ScheduleServices/ServiceDetail.fxml");
    }

    @FXML
    public void handleService3Click(ActionEvent event) {
        loadContent("/External/ScheduleServices/ServiceDetail.fxml");
    }

    @FXML
    public void handleService4Click(ActionEvent event) {
        loadContent("/External/ScheduleServices/ServiceDetail.fxml");
    }

    @FXML
    public void handleService5Click(ActionEvent event) {
        loadContent("/External/ScheduleServices/ServiceDetail.fxml");
    }
}
