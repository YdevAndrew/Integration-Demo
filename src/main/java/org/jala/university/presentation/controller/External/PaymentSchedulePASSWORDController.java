package org.jala.university.presentation.controller.External;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PaymentSchedulePASSWORDController {

    @FXML
    private Pane mainContent; // Painel principal onde os conteúdos serão exibidos

    /**
     * Método chamado ao clicar no botão "Pagar Agora".
     * Carrega a tela de senha para autenticação no painel principal.
     */
    @FXML
    private void SchedulePayNow(ActionEvent event) {
        try {
            // Carregar a tela de senha no painel principal
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/External/password/paymentSchedulePASSWORD.fxml"));
            Pane passwordPane = fxmlLoader.load();

            // Configurar o controlador da tela de senha
            PasswordPromptController passwordController = fxmlLoader.getController();
            passwordController.setOnPasswordVerified(() -> {
                // Após a verificação da senha, carregar a tela de sucesso no painel principal
                try {
                    FXMLLoader successLoader = new FXMLLoader(getClass().getResource("/External/ScheduleServices/paymentScheduleSucess.fxml"));
                    Pane successPane = successLoader.load();
                    mainContent.getChildren().setAll(successPane); // Substituir o conteúdo do painel principal
                } catch (IOException e) {
                    showErrorAlert("Erro ao carregar a tela de sucesso do pagamento.");
                    e.printStackTrace();
                }
            });

            // Substituir o conteúdo do painel com a tela de senha
            mainContent.getChildren().setAll(passwordPane);

        } catch (IOException e) {
            showErrorAlert("Erro ao carregar a tela de senha.");
            e.printStackTrace();
        }
    }

    /**
     * Método chamado ao clicar no botão "Cancelar".
     * Exibe uma confirmação e encerra o processo se confirmado.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Você realmente deseja cancelar o pagamento?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Cancelar Pagamento");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION, "Pagamento cancelado com sucesso.", ButtonType.OK);
                cancelAlert.setHeaderText("Cancelado");
                cancelAlert.showAndWait();
            }
        });
    }

    /**
     * Exibe um alerta de erro com a mensagem especificada.
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
}