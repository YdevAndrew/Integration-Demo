
package org.jala.university.presentation.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jala.university.presentation.controller.External.QRCodePaymentController;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class QRCodePaymentControllerTest_FluxoNavegacao_QRCodeIsValid extends ApplicationTest {

    private QRCodePaymentController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/gui/ManualPaymentScreens/QRCodePayment/QRCodePayment.fxml"));
        stage.setScene(new Scene(loader.load()));
        controller = loader.getController();
        stage.show();
    }

    @Test
    void testQRCodeIsValid_flow() {
        // Simula a seleção de um QR Code válido
        Platform.runLater(() -> {
            File file = new File("path/to/validQRCode.png"); // Substitua pelo caminho correto de um QR Code válido
            controller.onSelectQRCodeButtonClick(null);
            controller.decodeQRCode(file);
        });

        // Aguarda o status ser atualizado
        waitFor(5000);

        // Verifica se a mensagem de status foi alterada para "QR Code válido!"

        System.out.println("Status Label Text: " + controller.statusLabel.getText());

        assertEquals("QR Code válido!", controller.statusLabel.getText());

        assertTrue(controller.statusLabel.getStyle().contains("green"));
    }

    // Método auxiliar para aguardar um tempo para as atualizações de UI
    private void waitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


