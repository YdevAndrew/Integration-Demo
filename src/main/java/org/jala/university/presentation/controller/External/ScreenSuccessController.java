package org.jala.university.presentation.controller.External;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenSuccessController {

    @FXML
    private void handleOkButtonClick() {
        try {
            // Carregar a tela inicial
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/board/DashboardApp.fxml"));
            Pane root = fxmlLoader.load();

            // Obter o Stage atual e atualizar a cena
            Node okButton = null;
            Stage currentStage = (Stage) okButton.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            // Opcional: Alerta de erro em caso de falha
            System.err.println("Erro ao carregar a tela inicial.");
        }
    }
}
