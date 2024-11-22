package org.jala.university.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.jala.university.commons.presentation.BaseController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * This class controls the main dashboard view of the application.
 * It handles loading different sections of the dashboard,
 * displaying the current date, and toggling the visibility of the account balance.
 */
public class Dashboard extends BaseController {

    @FXML
    private Label balanceLabel;

    @FXML
    private Button toggleButton;

    @FXML
    private ImageView eyeIcon;

    @FXML
    private Label dateLabel;

    @FXML
    private Pane mainContent;

    private boolean isBalanceVisible = false;
    private double balance = 1234.56;

    /**
     * Loads the "My Cards" section into the main content area of the dashboard.
     *
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @FXML
    private void loadMyCards() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cards/cards_page.fxml"));
            Pane cardsPage = loader.load();
            mainContent.getChildren().setAll(cardsPage);
        } catch (IOException e) {
            System.err.println("Erro ao carregar cards_page.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads the "Request a Card" section into the main content area of the dashboard.
     *
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @FXML
    private void loadRequestCard() throws IOException {
        if(CardsPageController.validIfPhysicalExist() != null){
            if(CardsPageController.validIfVirtualExist() != null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/creditCardFXMLs/requestACard/request_a_card.fxml"));
                AnchorPane requestCard = loader.load();
                mainContent.getChildren().setAll(requestCard);
            }else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/creditCardFXMLs/requestAVirtualCard/request_a_virtual_card.fxml"));
                AnchorPane requestCard = loader.load();
                mainContent.getChildren().setAll(requestCard);


            }
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/creditCardFXMLs/requestACard/request_a_card.fxml"));
            AnchorPane requestCard = loader.load();
            mainContent.getChildren().setAll(requestCard);
        }




    }

    /**
     * Loads the "Invoice History" section into the main content area of the dashboard.
     *
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @FXML
    private void loadInvoiceHistory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/invoiceHistory/invoice_history.fxml"));
        AnchorPane invoiceHistory = loader.load();
        mainContent.getChildren().setAll(invoiceHistory);
    }

    /**
     * Initializes the controller. This method is automatically called after the FXML file has been loaded.
     * It sets up the action for the toggle button and displays the current date in the date label.
     */
    @FXML
    public void initialize() {
        toggleButton.setOnAction(event -> toggleBalanceVisibility());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM, dd", Locale.ENGLISH);
        dateLabel.setText(LocalDate.now().format(formatter));
        dateLabel.getStyleClass().add("balance-date");
    }


    /**
     * Toggles the visibility of the account balance.
     * If the balance is currently hidden, it will be shown; if it is shown, it will be hidden.
     * It also updates the eye icon to reflect the visibility state.
     */
    @FXML
    public void toggleBalanceVisibility() {
        if (balanceLabel.getText().equals("R$ ********")) {
            balanceLabel.setText(String.format("R$ %.2f", balance));
            eyeIcon.setImage(new Image(getClass().getResource("/img/eye_open.png").toExternalForm()));
        } else {
            balanceLabel.setText("R$ ********");
            eyeIcon.setImage(new Image(getClass().getResource("/img/eye.png").toExternalForm()));
        }
    }
}