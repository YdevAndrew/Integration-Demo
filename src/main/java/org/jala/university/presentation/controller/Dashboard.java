package org.jala.university.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jala.university.commons.presentation.BaseController;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
@Controller
public class Dashboard extends BaseController {

    public Button pixButton;
    public Button transactionHistButton;
    @FXML
    private VBox myCardsVBox;

    @FXML
    private VBox otherButtonsVBox;

    @FXML
    private Label balanceLabel;

    @FXML
    private Button transactionButton;

    @FXML
    private Pane contentPane;

    @FXML
    private Button toggleButton;

    @FXML
    private ImageView eyeIcon;

    @FXML
    private Label dateLabel;

    private boolean isBalanceVisible = false;  // Controle de visibilidade do saldo
    private double balance = 1234.56;  // Exemplo de valor do saldo, substitua pelo valor real

    @FXML
    public void initialize() {
        // Configura o botão de alternância de exibição do saldo
        toggleButton.setOnAction(event -> toggleBalanceVisibility());
        // Define a data atual no formato desejado e força o local para inglês
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM, dd", Locale.ENGLISH);
        dateLabel.setText(LocalDate.now().format(formatter));
        dateLabel.getStyleClass().add("balance-date");
        transactionButton.setOnAction(event -> loadTransactionView());
        pixButton.setOnAction(event -> loadPixView());
    }


    @FXML
    public void toggleBalanceVisibility() {
        if (balanceLabel.getText().equals("R$ ********")) {
            // Mostra o saldo e ajusta o ícone para "olho aberto"
            balanceLabel.setText(String.format("R$ %.2f", balance));
            eyeIcon.setImage(new Image(getClass().getResource("/img/eye_open.png").toExternalForm()));
        } else {
            // Esconde o saldo e ajusta o ícone para "olho fechado"
            balanceLabel.setText("R$ ********");
            eyeIcon.setImage(new Image(getClass().getResource("/img/eye.png").toExternalForm()));
        }
    }
    @FXML
    private void loadTransactionView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Style/Transection_paymentScreen.fxml"));
            Pane transactionPane = loader.load();
            contentPane.getChildren().clear();
            contentPane.getChildren().add(transactionPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void loadPixView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Style/Transection_TED&PIX.fxml"));
            Pane transactionPane = loader.load();
            contentPane.getChildren().clear();
            contentPane.getChildren().add(transactionPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void scheduleAPayment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SchedulePaymentScreens/SchedulePayment/SchedulePayment.fxml"));
        Pane schedulePayment = loader.load();
        contentPane.getChildren().setAll(schedulePayment);
    }

    @FXML
    private void QRCodePayment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ManualPaymentScreens/QRCodePayment/QRCodePayment.fxml"));
        Pane QRCodePayments = loader.load();
        contentPane.getChildren().setAll(QRCodePayments);
    }

    @FXML
    private void ScheduleService() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ScheduleServices/ButtonService.fxml"));
        Pane ScheduleService = loader.load();
        contentPane.getChildren().setAll(ScheduleService);
    }

    @FXML
    public void handleMyCardsClick() {
        boolean isVisible = myCardsVBox.isVisible();
        myCardsVBox.setVisible(!isVisible);
        myCardsVBox.setManaged(!isVisible);
        if (isVisible) {
            otherButtonsVBox.setVisible(true);
        } else {
            otherButtonsVBox.setVisible(true);
        }
    }
}
