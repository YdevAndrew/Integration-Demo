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

    private int failedAttempts = 0; // Contador de tentativas incorretas
    private static final int MAX_ATTEMPTS = 3; // Limite de tentativas
    private static final String CORRECT_PASSWORD = "1234"; // Senha correta

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
            passwordController.setPasswordHandler(password -> {
                System.out.println("Senha recebida: " + password); // Log da senha recebida
                if (CORRECT_PASSWORD.equals(password)) {
                    failedAttempts = 0; // Reseta o contador de tentativas após sucesso
                    try {
                        // Após a verificação da senha, carregar a tela de sucesso no painel principal
                        FXMLLoader successLoader = new FXMLLoader(getClass().getResource("/External/ScheduleServices/paymentScheduleSucess.fxml"));
                        Pane successPane = successLoader.load();
                        mainContent.getChildren().setAll(successPane); // Substituir o conteúdo do painel principal
                    } catch (IOException e) {
                        showErrorAlert("Erro ao carregar a tela de sucesso do pagamento.");
                        e.printStackTrace();
                    }
                } else {
                    failedAttempts++;
                    if (failedAttempts >= MAX_ATTEMPTS) {
                        // Exibe mensagem de bloqueio e redireciona para a tela de pagamento
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Número máximo de tentativas alcançado. Voltando para a tela de pagamento.", ButtonType.OK);
                        alert.showAndWait();

                        try {
                            FXMLLoader paymentLoader = new FXMLLoader(getClass().getResource("/External/QRCodePayment/QRCodePayment.fxml"));
                            Pane paymentPane = paymentLoader.load();
                            mainContent.getChildren().setAll(paymentPane); // Substituir o conteúdo do painel principal
                        } catch (IOException e) {
                            showErrorAlert("Erro ao carregar a tela de pagamento.");
                            e.printStackTrace();
                        }
                    } else {
                        // Exibe mensagem de erro com o número de tentativas restantes
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Senha incorreta. Tentativas restantes: " + (MAX_ATTEMPTS - failedAttempts), ButtonType.OK);
                        alert.showAndWait();
                    }
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
