package org.jala.university.presentation.controller.External;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ScreenSuccessController {
    @FXML
    public void handleClose(ActionEvent event) {
        // Fechar a janela de sucesso
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}