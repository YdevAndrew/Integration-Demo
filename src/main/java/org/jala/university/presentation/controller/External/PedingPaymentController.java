package org.jala.university.presentation.controller.External;

import ch.qos.logback.classic.net.SimpleSocketServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PedingPaymentController {

    @FXML
    private Pane mainContent;

    /**
     * Método chamado ao clicar no botão "Pagar Agora".
     * Abre a tela de senha para autenticação.
     */
    @FXML
    private void handlePayNow(ActionEvent event) {
        try {
            // Carregar a tela de senha
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/External/password/PasswordPrompt.fxml"));
            Pane root = fxmlLoader.load();

            // Configurar a tela de senha como um Stage modal
            Stage passwordStage = new Stage();
            passwordStage.setTitle("Payment Authentication");
            passwordStage.initModality(Modality.APPLICATION_MODAL); // Janela modal
            passwordStage.setScene(new Scene(root));
            passwordStage.setResizable(false);

            // Configurar o controlador para verificar a senha
            PasswordPromptController passwordController = fxmlLoader.getController();
            passwordController.setOnPasswordVerified(() -> {
                // Carregar a tela de pagamento feito com sucesso
                try {
                    FXMLLoader successLoader = new FXMLLoader(getClass().getResource("/External/ScheduleServices/LoadReceipt.fxml"));
                    Pane successRoot = successLoader.load();

                    // Configurar a nova cena
                    Stage successStage = new Stage();
                    successStage.setTitle("Payment Complete");
                    successStage.setScene(new Scene(successRoot));
                    successStage.setResizable(false);
                    successStage.show();




                    // Fechar a tela de senha
                    passwordStage.close();
                    successStage.close();
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading the successful payment screen.", ButtonType.OK);
                    alert.showAndWait();
                    e.printStackTrace();
                }
            });

            // Exibir a janela de senha
            passwordStage.showAndWait();


        } catch (IOException e) {
            // Exibir alerta em caso de erro ao carregar a tela
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading password screen.", ButtonType.OK);
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Método chamado ao clicar no botão "Cancelar".
     * Exibe uma confirmação e encerra o processo se confirmado.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to cancel the payment?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Cancel Payment");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION, "Pagamento cancelado com sucesso.", ButtonType.OK);
                cancelAlert.setHeaderText("Canceled");
                cancelAlert.showAndWait();
            }
        });
    }
}