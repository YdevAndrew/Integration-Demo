package org.jala.university.presentation.controller.Card;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jala.university.application.dto.dto_card.InvoiceDetailsDTO;
import org.jala.university.application.service.service_card.CreditCardClientService;
import org.jala.university.application.service.service_card.InvoicesService;
import org.jala.university.application.service.service_card.LimitCreditCardService;
import org.jala.university.domain.entity.entity_card.CreditCard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InvoiceHistoryController {

    @FXML
    private VBox mainContainer;
    @FXML
    private ScrollPane scrollContainer;


    private Label limitTotalLabel;
    private Label limitRemainingLabel;

    @FXML
    public void initialize() {
        try {

            List<CreditCard> creditCards = CreditCardClientService.searchCreditCardsByAccountId();

            if (creditCards.size() >= 2) {
                String firstCard = creditCards.get(0).getNumberCard();
                String secondCard = creditCards.get(1).getNumberCard();
                loadInvoices(firstCard, secondCard);
            } else if (creditCards.size() > 0) {
                String firstCard = creditCards.get(0).getNumberCard();
                loadInvoices(firstCard, null);
            } else {
                System.out.println("Card not found");
                Label noCardsLabel = new Label("Nenhum cartão encontrado.");
                noCardsLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
                mainContainer.getChildren().add(noCardsLabel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        createLimitInfoFields();


        mainContainer.setStyle("-fx-background-color: #0073e6;");


        scrollContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void createLimitInfoFields() {
        List<CreditCard> creditCards = CreditCardClientService.searchCreditCardsByAccountId();

        if (creditCards.isEmpty()) {
            // Se não houver cartões, não tenta acessar o índice
            Label noCardsLabel = new Label("Nenhum cartão encontrado.");
            noCardsLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
            mainContainer.getChildren().add(noCardsLabel);
            return; // Retorna para evitar erros
        }

        String firstCard = creditCards.get(0).getNumberCard(); // Agora é seguro acessar o índice 0

        HBox limitBox = new HBox(20);
        limitBox.setPadding(new Insets(20));

        limitTotalLabel = new Label("Total limit: R 0.00");
        limitRemainingLabel = new Label("Remaining Limit: R$ 0.00");

        limitTotalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        limitRemainingLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        updateLimitInfo(firstCard);
        limitBox.getChildren().addAll(limitTotalLabel, limitRemainingLabel);

        mainContainer.getChildren().add(0, limitBox);
        VBox.setMargin(limitBox, new Insets(10));
    }


    public void loadInvoices(String firstCreditCard, String secondCreditCard) throws SQLException {
        List<InvoiceDetailsDTO> invoicesFirstCard = InvoicesService.getDateInvoice(firstCreditCard);
        List<InvoiceDetailsDTO> invoicesSecondCard = secondCreditCard != null ? InvoicesService.getDateInvoice(secondCreditCard) : null;

        if ((invoicesFirstCard == null || invoicesFirstCard.isEmpty()) &&
                (invoicesSecondCard == null || invoicesSecondCard.isEmpty())) {
            Label noCardsLabel = new Label("No invoice found");
            noCardsLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: green;");
            mainContainer.getChildren().add(noCardsLabel);
            VBox.setVgrow(noCardsLabel, javafx.scene.layout.Priority.ALWAYS);
            mainContainer.setAlignment(javafx.geometry.Pos.CENTER);
            return;
        }

        mainContainer.getChildren().clear();

        if (invoicesFirstCard != null && !invoicesFirstCard.isEmpty()) {
            displayInvoicesForCard("Card 1: " + firstCreditCard, invoicesFirstCard);
        }

        if (invoicesSecondCard != null && !invoicesSecondCard.isEmpty()) {
            displayInvoicesForCard("Card 2: " + secondCreditCard, invoicesSecondCard);
        }
    }


    private void displayInvoicesForCard(String cardTitle, List<InvoiceDetailsDTO> invoices) {
        if (invoices == null || invoices.isEmpty()) {
            Label noInvoicesLabel = new Label("No invoices found for this card.");
            noInvoicesLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
            mainContainer.getChildren().add(noInvoicesLabel);
            return;
        }

        Map<YearMonth, List<InvoiceDetailsDTO>> groupedInvoices = invoices.stream()
                .collect(Collectors.groupingBy(invoice -> {
                    LocalDate invoiceDate = LocalDate.parse(invoice.getDatePayment());
                    return YearMonth.from(invoiceDate);
                }));

        for (Map.Entry<YearMonth, List<InvoiceDetailsDTO>> entry : groupedInvoices.entrySet()) {
            YearMonth month = entry.getKey();
            List<InvoiceDetailsDTO> monthInvoices = entry.getValue();

            VBox invoiceBox = createInvoiceBox(month, monthInvoices);
            mainContainer.getChildren().add(invoiceBox);
        }
    }


    private VBox createInvoiceBox(YearMonth month, List<InvoiceDetailsDTO> invoices) {
        VBox invoiceBox = new VBox();
        invoiceBox.setSpacing(10);
        invoiceBox.setPrefWidth(200);
        invoiceBox.setPrefHeight(250);
        invoiceBox.setPadding(new Insets(15));

        invoiceBox.setStyle("-fx-background-color: #003366; " +
                "-fx-border-radius: 15; " +
                "-fx-border-color: #ffffff; " +
                "-fx-border-width: 2; ");

        Label monthLabel = new Label("Invoices of " + month.getYear() + "-" + month.getMonthValue());
        monthLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        invoiceBox.getChildren().add(monthLabel);

        BigDecimal totalMonthValue = BigDecimal.ZERO;

        for (InvoiceDetailsDTO invoice : invoices) {
            VBox invoiceDetails = new VBox();
            invoiceDetails.setSpacing(5);
            invoiceDetails.setPrefWidth(180);
            invoiceDetails.setPrefHeight(50);
            invoiceDetails.setPadding(new Insets(5));

            LocalDate invoiceDate = LocalDate.parse(invoice.getDatePayment());
            String formattedDate = invoiceDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Label invoiceLabel = new Label("Date payment: " + formattedDate + " | Product name: " + invoice.getProductName() +
                    " | Barcode: " + invoice.getBarcode() +
                    " | Status payment: " + invoice.getInvoiceStatus() + " | Value: " + invoice.getValueInvoice().setScale(2, RoundingMode.HALF_UP) +
                    " | Purchase card: " + invoice.getNumberCard());

            invoiceLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
            invoiceDetails.getChildren().add(invoiceLabel);

            totalMonthValue = totalMonthValue.add(invoice.getValueInvoice());

            Button payButton = new Button("Pay");
            payButton.getStyleClass().add("payButton");
            invoiceDetails.getChildren().add(payButton);

            invoiceBox.getChildren().add(invoiceDetails);
        }

        Label totalLabel = new Label("Total of the Month: $ " + totalMonthValue.setScale(2, RoundingMode.HALF_UP));
        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        invoiceBox.getChildren().add(totalLabel);

        Button payAllButton = new Button("Pay Everything");
        payAllButton.setStyle("-fx-font-size: 16px; -fx-background-color: #ff5733; -fx-text-fill: white; -fx-padding: 10px 20px;");
        payAllButton.setOnAction(e -> handlePayAllButton(month, invoices));

        HBox payAllBox = new HBox(payAllButton);
        payAllBox.setAlignment(javafx.geometry.Pos.CENTER);
        invoiceBox.getChildren().add(payAllBox);

        return invoiceBox;
    }


    private void handlePayAllButton(YearMonth month, List<InvoiceDetailsDTO> invoices) {
      //Pay
    }
    private void updateLimitInfo(String creditCardNumber) {



        try {
            BigDecimal totalLimit = LimitCreditCardService.getLimitCreditCardInfo(creditCardNumber);
            BigDecimal usedLimit = InvoicesService.getTotalInvoicesByLimit(
                    LimitCreditCardService.findLimitCreditByCardNumber(creditCardNumber)
            );


            if (totalLimit == null) totalLimit = BigDecimal.ZERO;
            if (usedLimit == null) usedLimit = BigDecimal.ZERO;

            BigDecimal remainingLimit = totalLimit.subtract(usedLimit);

            System.out.println(InvoicesService.restLimit(creditCardNumber));
            limitTotalLabel.setText("Total Limit: $ " + totalLimit.setScale(2, RoundingMode.HALF_UP));
            limitRemainingLabel.setText("Remaining Limit: R$ " + InvoicesService.restLimit(creditCardNumber));
        } catch (Exception e) {
            e.printStackTrace();
            limitTotalLabel.setText("Total Limit: $ 0.00");
            limitRemainingLabel.setText("Remaining Limit: R$ 0.00");
        }
    }


}
