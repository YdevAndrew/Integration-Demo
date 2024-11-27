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
    private Pane mainContent; // Declara um Pane que será usado para manter o conteúdo principal da tela.

    @FXML
    public void handleSchedulePayment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/SchedulePaymentScreens/SchedulePayment/SchedulePayment.fxml")); // Cria uma instância de FXMLLoader para carregar o arquivo FXML especificado.
        Pane schedulePayment = loader.load(); // Carrega o arquivo FXML e cria o Pane correspondente.
        mainContent.getChildren().clear(); // Limpa quaisquer filhos existentes do Pane mainContent.
        mainContent.getChildren().add(schedulePayment); // Adiciona o novo Pane schedulePayment ao mainContent.
    }

    @FXML
    public void handlePendingPayment(ActionEvent event) throws IOException { // Método para lidar com pagamentos pendentes.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/External/ScheduleServices/PedingPayment.fxml")); // Cria um FXMLLoader para carregar o arquivo FXML de Pagamento Pendente.
        Pane root = fxmlLoader.load();
        Stage paymentPendingStage = new Stage();
        paymentPendingStage.setTitle("Pagamento Pendente");
        paymentPendingStage.setScene(new Scene(root));
        paymentPendingStage.show(); // Exibe a nova stage.
        mainContent.getChildren().clear(); // Limpa quaisquer filhos existentes do Pane mainContent.
    }
}