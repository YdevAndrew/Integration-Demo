package org.jala.university.presentation.controller.External;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PaymentScheduleSucessController {
    @FXML
    private AnchorPane mainContent; // Painel principal onde o conteúdo será carregado

    private static final String PASSWORD = "1234"; // Senha fixa para autenticação
    private Runnable onPasswordVerified; // Callback para lógica após verificação da senha

    /**
     * Método para redirecionar para o dashboard inicial.
     */
    @FXML
    private void handleOkButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/board/DashboardApp.fxml"));
            Pane root = fxmlLoader.load();

            // Obter o Stage atual e atualizar a cena
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Erro ao carregar a tela inicial.");
        }
    }

    /**
     * Método para abrir a tela de autenticação de senha.
     * Após a verificação da senha, executa a lógica passada no callback.
     */
    public void openPasswordPrompt() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/password/paymentSchedulePASSWORD.fxml"));
            Parent root = loader.load();

            PasswordPromptController controller = loader.getController();
            controller.setPasswordHandler(password -> {
                if (PASSWORD.equals(password)) {
                    // Se a senha estiver correta, executa o callback
                    if (onPasswordVerified != null) {
                        onPasswordVerified.run();
                    }
                } else {
                    showErrorAlert("Senha incorreta. Tente novamente.");
                }
            });

            Stage stage = new Stage();
            stage.setTitle("Autenticação de Pagamento");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            showErrorAlert("Erro ao carregar a tela de autenticação de senha.");
            e.printStackTrace();
        }
    }

    /**
     * Define o callback a ser executado após a verificação da senha.
     */
    public void setOnPasswordVerified(Runnable onPasswordVerified) {
        this.onPasswordVerified = onPasswordVerified;
    }

    /**
     * Exibe um alerta de erro com a mensagem especificada.
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Método para carregar a tela de pagamentos agendados.
     */
    @FXML
    private void handleViewScheduledPayments(ActionEvent event) {
        loadContent("/External/ScheduleServices/ButtonService.fxml");
    }

    /**
     * Método utilitário para carregar conteúdo no painel principal.
     *
     * @param fxmlPath Caminho para o arquivo FXML a ser carregado.
     */
    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node newContent = loader.load();
            mainContent.getChildren().setAll(newContent); // Substitui o conteúdo atual pelo novo
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar a tela: " + fxmlPath);
            showErrorAlert("Erro ao carregar a tela solicitada.");
        }
    }
}
