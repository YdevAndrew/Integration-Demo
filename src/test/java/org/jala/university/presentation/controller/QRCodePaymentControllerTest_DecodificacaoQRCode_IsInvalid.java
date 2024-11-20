package org.jala.university.presentation.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class QRCodePaymentControllerTest_DecodificacaoQRCode_IsInvalid extends ApplicationTest {

    private QRCodePaymentController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ManualPaymentScreens/QRCodePayment/QRCodePayment.fxml"));
        stage.setScene(new Scene(loader.load()));
        controller = loader.getController();
        stage.show();
    }

    @Test
    void testQRCodeDecodingIsInvalid() {
        // Mock do arquivo com um QR Code inválido
        File invalidQRCodeFile = Mockito.mock(File.class);

        // Simula que ocorreu um erro na decodificação
        Mockito.doThrow(new Exception("QRCode inválido")).when(controller).decodeQRCode(invalidQRCodeFile);

        // Tenta decodificar o QR Code
        try {
            controller.decodeQRCode(invalidQRCodeFile);
        } catch (Exception e) {
            assertEquals("QRCode inválido", controller.statusLabel.getText());
            assertTrue(controller.statusLabel.getStyle().contains("red"));
        }
    }
}



