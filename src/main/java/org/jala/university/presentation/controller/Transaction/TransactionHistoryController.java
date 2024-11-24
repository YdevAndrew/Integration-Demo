package org.jala.university.presentation.controller.Transaction;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.jala.university.application.dto.dto_transaction.PaymentHistoryDTO;
import org.jala.university.application.service.service_transaction.PaymentHistoryService;
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

        // Adicionando filtros
        filterChoiceBox.setItems(FXCollections.observableArrayList("All", "Received", "Sent", "Pending", "Completed"));
        filterChoiceBox.getSelectionModel().select(0); // Seleciona "All" como padrão

        loadTransactionHistory();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterTransactionHistory(newValue));
        filterChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> loadTransactionHistory());
    }

    private void loadTransactionHistory() {
        transactionListBox.getChildren().clear();

        String selectedFilter = filterChoiceBox.getValue();
        List<PaymentHistoryDTO> paymentHistory = null;

        // Aplica o filtro conforme o tipo de transação ou status
        switch (selectedFilter) {
            case "Received":
                paymentHistory = paymentHistoryService.getPaymentHistoryFiltesSenderOrReceiver(34, false); // Filtro para transações recebidas
                break;
            case "Sent":
                paymentHistory = paymentHistoryService.getPaymentHistoryFiltesSenderOrReceiver(34, true); // Filtro para transações enviadas
                break;
            case "Pending":
                paymentHistory = paymentHistoryService.getPaymentHistoryFiltesCompletedOrScheduled(34, false); // Filtro para transações pendentes
                break;
            case "Completed":
                paymentHistory = paymentHistoryService.getPaymentHistoryFiltesCompletedOrScheduled(34, true); // Filtro para transações concluídas
                break;
            default:
                paymentHistory = paymentHistoryService.getPaymentHistory(34); // Sem filtro
        }

        // Exibir as transações
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
