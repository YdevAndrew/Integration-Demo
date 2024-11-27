package org.jala.university.presentation.controller.External;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jala.university.presentation.controller.Loan.SpringFXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ScreenNoPaymentSchedule {
    @Autowired
    private SpringFXMLLoader springFXMLLoader;
    @FXML
    private Pane mainContent; // Certifique-se de que existe um contêiner correspondente no arquivo FXML.

    @FXML
    public void handleSchedulePayment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/SchedulePaymentScreens/SchedulePayment/SchedulePayment.fxml"));
        Pane schedulePayment = loader.load();
        mainContent.getChildren().clear(); // Limpa o conteúdo existente, se necessário.
        mainContent.getChildren().add(schedulePayment); // Adiciona o novo conteúdo.
    }
    @FXML
    public void handlePendingPayment(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/External/ScheduleServices/PedingPayment.fxml"));
        Pane root = fxmlLoader.load();


        Stage paymentPedingStage = new Stage();
        paymentPedingStage.setTitle(" Pagamento Pendente");
        paymentPedingStage.setScene(new Scene(root));
        paymentPedingStage.show();
    }



}
