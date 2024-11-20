package org.jala.university.presentation.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.application.Platform;

import static org.junit.jupiter.api.Assertions.*;

class SchedulePaymentControllerTest extends ApplicationTest {

    private SchedulePaymentController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SchedulePaymentScreens/SchedulePayment/SchedulePayment.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void validateAmount_belowMinimum_showsError() {
        // Arrange: Simula a inserção do valor "0,50" no campo de texto
        controller.amountField.setText("0,50");

        // Garantir que a interação com a UI aconteça no thread correto (JavaFX thread)
        Platform.runLater(() -> {
            // Act: Chama a validação dos campos, o que deve disparar o alerta
            boolean result = controller.validateAllFields();

            // Assert: Verifica se a validação falhou (o valor é menor que o mínimo)
            assertFalse(result, "Expected validateAllFields to return false for amount below minimum.");
        });

        // Espera um pouco para o alerta ser mostrado
        waitForAlert(6000);  // Tempo suficiente para o alerta ser exibido

        // Assert: Verifica se o alerta foi mostrado na interface
        assertTrue(isAlertPresent(), "Expected an alert to be shown for amount below minimum.");
    }

    // Método auxiliar para verificar a presença do alerta
    private boolean isAlertPresent() {
        // TestFX: Verifica a presença do alerta na interface
        return lookup(".alert").queryAll().size() > 0;
    }

    // Método auxiliar para evitar conflito com o método sleep do TestFX
    private void waitForAlert(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



