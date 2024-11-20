package org.jala.university.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ButtonServiceController {
    @FXML
    private AnchorPane mainContent; // Altere para AnchorPane para corresponder ao tipo em ButtonService.fxml

    @FXML
    public void handleService1Click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ScheduleServices/ServiceDetail.fxml"));
        Pane serviceDetailContent = loader.load();
        mainContent.getChildren().setAll(serviceDetailContent);
    }

    @FXML
    public void handleService2Click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ScheduleServices/ServiceDetail.fxml"));
        Pane serviceDetailContent = loader.load();
        mainContent.getChildren().setAll(serviceDetailContent);
    }

    @FXML
    public void handleService3Click(ActionEvent event) {
        System.out.println("Service 3 clicked");
        // Adicione a ação desejada aqui
    }

    @FXML
    public void handleService4Click(ActionEvent event) {
        System.out.println("Service 4 clicked");
        // Adicione a ação desejada aqui
    }

    @FXML
    public void handleService5Click(ActionEvent event) {
        System.out.println("Service 5 clicked");
        // Adicione a ação desejada aqui
    }
}