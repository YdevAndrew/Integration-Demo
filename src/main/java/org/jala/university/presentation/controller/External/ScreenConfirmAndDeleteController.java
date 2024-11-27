package org.jala.university.presentation.controller.External;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.jala.university.commons.presentation.BaseController;
import org.jala.university.presentation.controller.Loan.SpringFXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class ScreenConfirmAndDeleteController extends BaseController {
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    // Método chamado quando o usuário clica no botão "Confirmar"
    @FXML
    private void handleConfirm(ActionEvent event) {
        // Lógica para excluir o serviço
        System.out.println("Serviço excluído com sucesso!");

        // Fechar a janela de confirmação
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();

        try {
            // Carrega o FXML de sucesso na exclusão
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/ScheduleServices/ScreenDeleteSuccess.fxml"));
            Parent root = loader.load();

            // Inicializa a nova cena
            stage = new Stage();
            stage.setTitle("Sucesso");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Caso ocorra algum erro, você pode tratar isso aqui
        }
    }


    @FXML
    public void handleCancel(ActionEvent event) {
        // Fechar a janela sem realizar nenhuma ação
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}