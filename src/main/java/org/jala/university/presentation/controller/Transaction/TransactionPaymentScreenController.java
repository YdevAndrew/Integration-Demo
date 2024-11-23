package org.jala.university.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jala.university.application.dto.AccountDto;
import org.jala.university.application.service.AccountServiceImpl;
import org.jala.university.application.service.PaymentHistoryService;
import org.jala.university.commons.presentation.BaseController;
import org.jala.university.domain.entity.Account;
import org.jala.university.domain.entity.Customer;
import org.jala.university.presentation.MainView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class TransactionPaymentScreenController extends BaseController {

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Autowired
    private PaymentHistoryService paymentHistoryService;

    @FXML
    private TextField accountField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField valueField;

    @FXML
    private DatePicker schedulingDatePicker;

    @FXML
    private Label accountErrorLabel;

    @FXML
    private Label valueErrorLabel;

    @FXML
    private Label schedulingErrorLabel;

    @FXML
    private Button advanceButton;

    @FXML
    private Pane contentPane;

    @FXML
    private VBox contactsListVBox;

    private static String savedAccountNumber;
    private static String savedDescription;
    private static String savedValue;
    private static LocalDate savedSchedulingDate;

    // Temporary for testing purposes
    private Integer userId = 34;
    private String userAccountNumber; // Stores the user's own account number

    @FXML
    public void initialize() {
        // Load the user's account number
        userAccountNumber = accountServiceImpl.findById(userId)
                .map(Account::getAccountNumber)
                .orElse(null);

        if (savedAccountNumber != null) accountField.setText(savedAccountNumber);
        if (savedDescription != null) descriptionField.setText(savedDescription);
        if (savedValue != null) valueField.setText(savedValue);
        if (savedSchedulingDate != null) schedulingDatePicker.setValue(savedSchedulingDate);

        validateFields();

        loadSavedContacts();

        accountField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        valueField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        schedulingDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> validateFields());

        advanceButton.setOnAction(event -> loadTransactionPasswordScreen());
    }

    @FXML
    private void loadSavedContacts() {
        contactsListVBox.getChildren().clear();

        List<AccountDto> savedContacts = paymentHistoryService.getContactList(userId);

        for (AccountDto contact : savedContacts) {
            Optional<Customer> customerOptional = accountServiceImpl.getCustomerByAccountId(contact.getId());

            customerOptional.ifPresent(customer -> {
                Button contactButton = new Button(customer.getFullName() + " (" + contact.getAccountNumber() + ")");
                contactButton.setPrefHeight(66.0);
                contactButton.setPrefWidth(231.0);
                contactButton.getStyleClass().add("list-container");

                contactButton.setOnAction(event -> {
                    accountField.setText(contact.getAccountNumber());
                    descriptionField.setText("Payment to " + customer.getFullName());
                    valueField.requestFocus();
                });

                contactsListVBox.getChildren().add(contactButton);
            });
        }
    }

    private void validateFields() {
        boolean isValid = true;

        // Validate account number
        if (accountField.getText().isEmpty()) {
            accountErrorLabel.setText("Account number cannot be empty.");
            isValid = false;
        } else if (!accountField.getText().matches("\\d{4}-\\d{6}")) {
            accountErrorLabel.setText("Account number must be in the format 0001-448904.");
            isValid = false;
        } else if (accountField.getText().equals(userAccountNumber)) {
            accountErrorLabel.setText("You cannot transfer to your own account.");
            isValid = false;
        } else {
            accountErrorLabel.setText("");
        }

        // Validate value
        try {
            BigDecimal value = new BigDecimal(valueField.getText().replace(",", "."));
            if (value.compareTo(BigDecimal.ZERO) <= 0) {
                valueErrorLabel.setText("Value must be greater than zero.");
                isValid = false;
            } else {
                valueErrorLabel.setText("");
            }
        } catch (NumberFormatException e) {
            valueErrorLabel.setText("Invalid value format.");
            isValid = false;
        }

        // Validate scheduling date
        if (schedulingDatePicker.getValue() != null && schedulingDatePicker.getValue().isBefore(LocalDate.now())) {
            schedulingErrorLabel.setText("Scheduling date cannot be in the past.");
            isValid = false;
        } else {
            schedulingErrorLabel.setText("");
        }

        advanceButton.setDisable(!isValid);
    }

    private void loadTransactionPasswordScreen() {
        try {
            savedAccountNumber = accountField.getText();
            savedDescription = descriptionField.getText();
            savedValue = valueField.getText();
            savedSchedulingDate = schedulingDatePicker.getValue() != null
                    ? schedulingDatePicker.getValue()
                    : LocalDate.now();

            BigDecimal value = new BigDecimal(savedValue.replace(",", "."));

            Integer accountId = accountServiceImpl.findByAccountNumber(savedAccountNumber)
                    .map(Account::getId)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found."));

            Customer customer = accountServiceImpl.getCustomerByAccountId(accountId)
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found for the account."));

            String nameReceiver = customer.getFullName();
            String cpfReceiver = customer.getCpf();

            FXMLLoader loader = MainView.getSpringFXMLLoader().load("/Style/Transection_Password.fxml");
            Pane transactionPasswordPane = loader.load();

            TransactionPasswordController passwordController = loader.getController();
            passwordController.setTransactionDetails(
                    savedAccountNumber,
                    savedDescription,
                    value,
                    nameReceiver,
                    cpfReceiver,
                    savedSchedulingDate
            );

            contentPane.getChildren().clear();
            contentPane.getChildren().add(transactionPasswordPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
