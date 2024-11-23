package org.jala.university.presentation.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.jala.university.application.dto.PaymentHistoryDTO;
import org.jala.university.application.service.PaymentHistoryService;
import org.jala.university.commons.presentation.BaseController;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TransactionHistoryController extends BaseController {

    @FXML
    private TextField searchField;

    @FXML
    private ChoiceBox<String> filterChoiceBox;

    @FXML
    private ScrollPane transactionScrollPane;

    @FXML
    private VBox transactionListBox;

    private final PaymentHistoryService paymentHistoryService;

    public TransactionHistoryController(PaymentHistoryService paymentHistoryService) {
        this.paymentHistoryService = paymentHistoryService;
    }

    @FXML
    public void initialize() {
        transactionScrollPane.setFitToWidth(true);
        transactionScrollPane.setContent(transactionListBox);

        filterChoiceBox.setItems(FXCollections.observableArrayList("Type", "Date"));
        filterChoiceBox.getSelectionModel().select(0);

        loadTransactionHistory();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterTransactionHistory(newValue));
        filterChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> loadTransactionHistory());
    }

    private void loadTransactionHistory() {
        transactionListBox.getChildren().clear();

        List<PaymentHistoryDTO> paymentHistory = paymentHistoryService.getPaymentHistory(34);

        for (PaymentHistoryDTO payment : paymentHistory) {
            VBox transactionItem = createTransactionItem(payment);
            transactionListBox.getChildren().add(transactionItem);
        }
    }

    private VBox createTransactionItem(PaymentHistoryDTO payment) {
        VBox itemBox = new VBox();
        itemBox.setSpacing(5);
        itemBox.getStyleClass().add("transaction-item");

        Label titleLabel = new Label(payment.getDescription());
        titleLabel.getStyleClass().add("transaction-title");

        String details = String.format("%s, %s-%s (%s)",
                payment.getNameReceiver(),
                payment.getAgencyReceiver(),
                payment.getAccountReceiver(),
                payment.getBankNameReceiver());
        Label detailsLabel = new Label(details);
        detailsLabel.getStyleClass().add("transaction-details");

        String amountAndDate = String.format("%s   %s",
                payment.getAmount().toPlainString(),
                payment.getTransactionDate().toLocalDate().toString());
        Label amountLabel = new Label(amountAndDate);
        amountLabel.getStyleClass().add("transaction-amount-date");

        itemBox.getChildren().addAll(titleLabel, detailsLabel, amountLabel);

        return itemBox;
    }

    private void filterTransactionHistory(String searchTerm) {
        transactionListBox.getChildren().clear();

        List<PaymentHistoryDTO> filteredHistory = paymentHistoryService.getPaymentHistory(34);
        filteredHistory = filteredHistory.stream()
                .filter(payment -> payment.getDescription().toLowerCase().contains(searchTerm.toLowerCase()))
                .toList();

        // Populate VBox with filtered transactions
        for (PaymentHistoryDTO payment : filteredHistory) {
            VBox transactionItem = createTransactionItem(payment);
            transactionListBox.getChildren().add(transactionItem);
        }
    }
}
