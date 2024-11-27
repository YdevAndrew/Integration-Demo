package org.jala.university.presentation.controller.External;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jala.university.commons.presentation.BaseController;
import org.springframework.stereotype.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.function.Consumer;
import java.io.IOException;

@Controller
public class PasswordPromptController extends BaseController {

    @FXML
    private PasswordField passwordField;
    private Consumer<String> passwordHandler;
    private String path;
    private ManualPaymentInformationController paymentDetailsController;

    private static final String CORRECT_PASSWORD = "1234";
    private Runnable onPasswordVerified;
    private ManualPaymentInformationController manualPaymentInformationController;

    private int failedAttempts = 0; // Contador de tentativas incorretas
    private static final int MAX_ATTEMPTS = 3; // Limite de tentativas

    public void setPasswordHandler(Consumer<String> handler) {
        this.passwordHandler = handler;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    public void setPaymentDetailsController(ManualPaymentInformationController paymentDetailsController) {
        this.paymentDetailsController = paymentDetailsController;
    }

    public void setManualPaymentInformationController(ManualPaymentInformationController controller) {
        this.manualPaymentInformationController = controller;
    }

    @FXML
    public void initialize() {
        System.out.println("Inicializando controlador. mainContent: " + mainContent);
    }

    @FXML
    private AnchorPane mainContent;

    @FXML
    private void onConfirmButtonClick() {
        String enteredPassword = passwordField.getText();

        if (CORRECT_PASSWORD.equals(enteredPassword)) {
            failedAttempts = 0; // Reseta o contador ao inserir a senha correta
            if (passwordHandler != null) {
                passwordHandler.accept(enteredPassword);
            }

            try {
                // Exibe LoadReceipt no painel principal
                loadContent("/External/ScheduleServices/LoadReceipt.fxml");

                // Inicia a transição após 3 segundos
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
                    try {
                        // Exibe PaymentReceipt no painel principal
                        loadContent("/External/PaymentReceipt/PaymentReceipt.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar o comprovante de pagamento.", ButtonType.OK);
                        alert.showAndWait();
                    }
                }));
                timeline.setCycleCount(1);
                timeline.play();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar o comprovante.", ButtonType.OK);
                alert.showAndWait();
            }
        } else {
            failedAttempts++;
            if (failedAttempts >= MAX_ATTEMPTS) {
                // Bloqueia o usuário após 3 tentativas incorretas
                Alert alert = new Alert(Alert.AlertType.ERROR, "Você atingiu o número máximo de tentativas. Voltando para a tela de pagamento.", ButtonType.OK);
                alert.showAndWait();

                try {
                    // Redireciona para a tela de pagamento
                    loadContent("/External/QRCodePayment/QRCodePayment.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar a tela de pagamento.", ButtonType.OK);
                    errorAlert.showAndWait();
                }
            } else {
                // Exibe mensagem de erro para tentativa incorreta
                Alert alert = new Alert(Alert.AlertType.WARNING, "Senha incorreta. Tentativas restantes: " + (MAX_ATTEMPTS - failedAttempts), ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    private void loadContent(String fxmlPath) throws IOException {
        if (mainContent == null) {
            System.out.println("Erro: mainContent não foi inicializado.");
            return;
        }

        // Carrega o conteúdo do FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent newContent = loader.load();

        // Substitui o conteúdo no painel principal
        mainContent.getChildren().setAll(newContent);
    }

    @FXML
    private void onCancelButtonClick() {
        Stage stage = (Stage) passwordField.getScene().getWindow();
        stage.close();
    }

    public void setOnPasswordVerified(Runnable onPasswordVerified) {
        this.onPasswordVerified = onPasswordVerified;
    }

    public void setPasswordHandler(Object o) {
        // Método vazio, pode ser removido ou utilizado conforme necessário
    }

    public void setScreenConfirmAndDeleteController(ScreenConfirmAndDeleteController screenConfirmAndDeleteController) {
        // Método vazio, pode ser removido ou utilizado conforme necessário
    }

    public void setSchedulePaymentInformationController(SchedulePaymentInformationController schedulePaymentInformationController) {
    }
}