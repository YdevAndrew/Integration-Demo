package org.jala.university.presentation.controller.External;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ScreenConfirmAndDeleteController {
    @FXML
    public void handleConfirm(ActionEvent event) {
        // Lógica para excluir o serviço
        System.out.println("Serviço excluído com sucesso!");

        // Fechar a janela de confirmação
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    public void handleCancel(ActionEvent event) {
        // Fechar a janela sem realizar nenhuma ação
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}