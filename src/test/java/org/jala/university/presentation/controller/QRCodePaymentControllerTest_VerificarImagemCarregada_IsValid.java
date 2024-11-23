package org.jala.university.presentation.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jala.university.presentation.controller.External.QRCodePaymentController;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class QRCodePaymentControllerTest_VerificarImagemCarregada_IsValid extends ApplicationTest {

    private QRCodePaymentController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/gui/ManualPaymentScreens/QRCodePayment/QRCodePayment.fxml"));
        stage.setScene(new Scene(loader.load()));
        controller = loader.getController();
        stage.show();
    }

    @Test
    void testImageLoadingAndQRCodeDisplay() {
        // Simula a escolha do arquivo de imagem
        Platform.runLater(() -> {
            File file = new File("path/to/validQRCode.png");  // Coloque o caminho correto
            controller.onSelectQRCodeButtonClick(null);
            controller.decodeQRCode(file);
        });

        // Aguarda o tempo necessário para o processamento da imagem
        waitFor(2000);

        // Verifica se a imagem foi carregada no ImageView
        assertNotNull(controller.qrCodeImageView.getImage());
        assertTrue(controller.qrCodeImageView.getImage() instanceof Image);
    }

    private void waitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}




