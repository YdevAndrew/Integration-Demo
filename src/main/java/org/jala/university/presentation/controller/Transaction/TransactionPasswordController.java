package org.jala.university.presentation.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jala.university.application.dto.PaymentHistoryDTO;
import org.jala.university.application.service.PaymentHistoryService;
import org.jala.university.presentation.SpringFXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class TransactionPasswordController {

    @Autowired
    private PaymentHistoryService paymentHistoryService;

    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @FXML
    private Button backButton;
    @FXML
    private Button transferButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Pane contentPane2;
    @FXML
    private TextFlow nameFlow;
    @FXML
    private TextFlow descriptionFlow;
    @FXML
    private TextFlow bankFlow;
    @FXML
    private TextFlow cpfFlow;
    @FXML
    private TextFlow keyFlow;
    @FXML
    private TextFlow amountFlow;
    @FXML
    private TextFlow dateFlow;
    @FXML
    private Label passwordErrorLabel;

    private String accountNumber;
    private String description;
    private BigDecimal value;
    private String cpfReceiver;
    private String agencyReceiver;
    private String accountReceiver;
    private String nameReceiver;

    private Stage loadingStage;
    private LocalDate scheduleDate;
    private boolean cancelTransaction = false;

    //######################################################################
    // Placeholder user ID
    private Integer userId = 34;
    //######################################################################

    public void setTransactionDetails(String accountNumber, String description, BigDecimal value, String nameReceiver, String cpfReceiver, LocalDate schedulingDate) {
        this.accountReceiver = accountNumber;
        this.description = description;
        this.value = value;
        this.nameReceiver = nameReceiver;
        this.cpfReceiver = cpfReceiver;
        this.scheduleDate = schedulingDate;

        setTextFlowContent(descriptionFlow, "Description: " + description);
        setTextFlowContent(bankFlow, "Bank: Jala Bank");
        setTextFlowContent(keyFlow, "Account: " + accountNumber);
        setTextFlowContent(amountFlow, "Amount: " + value);
        setTextFlowContent(dateFlow, "Date: " + (schedulingDate != null ? schedulingDate : LocalDate.now()));
        setTextFlowContent(nameFlow, "Name: " + nameReceiver);
        setTextFlowContent(cpfFlow, "CPF/CNPJ: " + cpfReceiver);
    }

    @FXML
    public void initialize() {
        backButton.setOnAction(event -> goBack());
        transferButton.setOnAction(event -> processTransaction());

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
    }

    private void setTextFlowContent(TextFlow textFlow, String content) {
        textFlow.getChildren().clear();
        Text text = new Text(content);
        textFlow.getChildren().add(text);
    }

    private void validateFields() {
        boolean isValid = true;

        if (passwordField.getText().isEmpty()) {
            passwordErrorLabel.setText("Password cannot be empty.");
            passwordErrorLabel.setVisible(true);
            isValid = false;
        } else if (passwordField.getText().length() < 4) {
            passwordErrorLabel.setText("Password must be at least 4 characters long.");
            passwordErrorLabel.setVisible(true);
            isValid = false;
        } else {
            passwordErrorLabel.setText("");
            passwordErrorLabel.setVisible(false);
        }

        transferButton.setDisable(!isValid);
    }

    private void processTransaction() {
        if (passwordIsValid(passwordField.getText())) {
            clearErrorStyles();
            cancelTransaction = false;
            showLoadingScreen();

            Task<Void> transactionTask = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    for (int i = 1; i <= 100; i++) {
                        if (cancelTransaction) {
                            updateMessage("Transaction canceled.");
                            break;
                        }
                        updateProgress(i, 100);
                        Thread.sleep(50);
                    }

                    if (!cancelTransaction) {
                        try {
                            PaymentHistoryDTO paymentHistoryDTO = PaymentHistoryDTO.builder()
                                    .amount(value)
                                    .transactionDate(scheduleDate.atStartOfDay())
                                    .description(description)
                                    .cpfReceiver(cpfReceiver)
                                    .agencyReceiver("4545")
                                    .accountReceiver(accountReceiver)
                                    .nameReceiver(nameReceiver)
                                    .bankNameReceiver("JalaU Bank")
                                    .build();

                            paymentHistoryService.createPaymentHistory(userId, paymentHistoryDTO,"TRANSACTION");
                            Platform.runLater(() -> {
                                loadingStage.close();
                                showSuccessMessage("Transaction completed successfully!");
                            });
                        } catch (Exception e) {
                            Platform.runLater(() -> {
                                showError("Error processing transaction: " + e.getMessage());
                                loadingStage.close();
                            });
                        }
                    } else {
                        Platform.runLater(() -> {
                            loadingStage.close();
                            showSuccessMessage("Transaction canceled.");
                        });
                    }

                    return null;
                }
            };

            ProgressBar progressBar = (ProgressBar) loadingStage.getScene().lookup("#progressBar");
            progressBar.progressProperty().bind(transactionTask.progressProperty());

            new Thread(transactionTask).start();
        } else {
            showError("Invalid password. Please try again.");
        }
    }

    private void showLoadingScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Style/Transection_LoadingScreen.fxml"));
            VBox loadingBox = loader.load();

            Button cancelButton = (Button) loadingBox.lookup("#cancelButton");
            cancelButton.setOnAction(event -> {
                cancelTransaction = true;
                loadingStage.close();
            });

            loadingStage = new Stage();
            loadingStage.initModality(Modality.APPLICATION_MODAL);
            loadingStage.setScene(new Scene(loadingBox));
            loadingStage.setTitle("Processing Transaction");
            loadingStage.setOnCloseRequest(event -> cancelTransaction = true);
            loadingStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showSuccessMessage(String message) {
        System.out.println(message);
    }

    private boolean passwordIsValid(String password) {
        return password.equals("expectedPassword");
    }

    private void showError(String errorMessage) {
        passwordErrorLabel.setText(errorMessage);
        passwordErrorLabel.setVisible(true);

        passwordField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
    }

    private void clearErrorStyles() {
        passwordErrorLabel.setText("");
        passwordErrorLabel.setVisible(false);
        passwordField.setStyle(null);
    }

    private void goBack() {
        try {
            FXMLLoader loader = springFXMLLoader.load("/Style/Transection_paymentScreen.fxml");
            Pane mainPagePane = loader.load();

            contentPane2.getChildren().clear();
            contentPane2.getChildren().add(mainPagePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
