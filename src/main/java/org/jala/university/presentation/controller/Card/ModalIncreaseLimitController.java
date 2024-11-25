package org.jala.university.presentation.controller.Card;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.jala.university.application.dao_card.CustomerInformationDAO;
import org.jala.university.application.service.service_card.CreditCardService;
import org.jala.university.application.service.service_card.IncreaseLimit;
import org.jala.university.application.service.service_card.LimitCreditCardService;

import java.sql.SQLException;

/**
 * The type Modal increase limit controller.
 */
public class ModalIncreaseLimitController {

    @FXML
    private Text currentLimit;

    @FXML
    private TextField textFieldLimit;

    @FXML
    private Button button100;

    @FXML
    private Button button250;

    @FXML
    private Button button500;

    @FXML
    private Button okButton;

    private double currentLimitValue = Double.parseDouble(String.valueOf(LimitCreditCardService.findLimitCreditByCardNumber(CardsPageController.getCardClicked()).getLimit_credit()));

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        updateCurrentLimitDisplay();
        textFieldLimit.setText(String.valueOf(currentLimitValue));
        // Update the limit value display as the user types

        textFieldLimit.textProperty().addListener((observable, oldValue, newValue) -> validateAndDisplay(newValue));

        button100.setOnAction(event -> increaseLimit(100.00));
        button250.setOnAction(event -> increaseLimit(250.00));
        button500.setOnAction(event -> increaseLimit(500.00));
        okButton.setOnAction(event -> confirmLimit());
    }

    /**
     * Update the display of limit.
     */
    private void updateCurrentLimitDisplay() {
        currentLimit.setText("Your current limit is R$ " + String.format("%.2f",   LimitCreditCardService.findLimitCreditByCardNumber(CardsPageController.getCardClicked()).getLimit_credit()));
    }

    /**
     * Validates the value entered and updates the display.
     * @param enteredValue The entered value.
     */
    private void validateAndDisplay(String enteredValue) {
        String cleanedValue = enteredValue.replaceAll("[^0-9,\\.]", "");
        if (!cleanedValue.isEmpty()) {
            try {
                double newLimit = Double.parseDouble(cleanedValue.replace(",", "."));
                currentLimit.setText("Your current limit is R$ " + String.format("%.2f", LimitCreditCardService.findLimitCreditByCardNumber(CardsPageController.getCardClicked()).getLimit_credit()));
            } catch (NumberFormatException e) {
                currentLimit.setText("Invalid value");
            }
        } else {
            updateCurrentLimitDisplay();
        }
    }

    /**
     * Increases the limit by a specified amount.
     * @param amount The amount to increase.
     */
    private void increaseLimit(double amount) {
        currentLimitValue += amount;
        updateCurrentLimitDisplay();
        textFieldLimit.setText(String.format("%.2f", currentLimitValue));
    }

    /**
     * Handles the action when the user confirms the new limit.
     */
    private void confirmLimit() {
        String enteredValue = textFieldLimit.getText().replace("R$", "").replace(",", "").trim();
        try {
            double newLimit = Double.parseDouble(enteredValue);
            currentLimitValue = newLimit;
            System.out.println("New limit confirmed: R$ " + currentLimitValue);

            // Call the backend service to update the limit
            updateLimitOnBackend(currentLimitValue);

        } catch (NumberFormatException e) {
            System.err.println("Invalid value for the limit: " + enteredValue);
            // Display an error alert in English
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Invalid limit value. Please enter a valid number.",
                    ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Sends a request to the backend to update the credit card limit.
     *
     * @param newLimit The new credit card limit to be set.
     */
    private void updateLimitOnBackend(double newLimit) {
        try {
            if(IncreaseLimit.increaseLimitIfNeeded(CreditCardService.getCreditCardByNumberCard(CardsPageController.getCardClicked()).getNumberCard())) {
                    showSuccessAlert();
                    sendCodeByEmail(true);


            }else {
                showSuccessAlert();
                sendCodeByEmail(false);

            }
        } catch (Exception e) {
            // Handle any exceptions
            showFailureAlert();
        }
    }

    /**
     * Displays a success alert to the user.
     */
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "We are analyzing your request. You will receive an email soon.",
                ButtonType.OK);
        alert.showAndWait();
    }

    public static void main(String[] args) {

        System.out.println(IncreaseLimit.increaseLimitIfNeeded((CreditCardService.getCreditCardByNumberCard("8676963344067751").getNumberCard())));
    }

    /**
     * Displays a failure alert if something goes wrong.
     */
    private void showFailureAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                "Failed to process your request. Please try again later.",
                ButtonType.OK);
        alert.showAndWait();
    }



    public static void sendCodeByEmail(boolean isValid) throws SQLException {

        if(isValid) {
            String body = "Good news, your limit has been increased to use";
            String subjectSucess = "JalaU limit increase";

            ChangePasswordController.sendEmail(CustomerInformationDAO.selectCustomer().get(0).getEmail(), subjectSucess, body);
        }else {
            String body = "We did the analysis and unfortunately it was not possible to increase your limit now, try using it more.";
            String subjectFall = "JalaU limit increase";

            ChangePasswordController.sendEmail(CustomerInformationDAO.selectCustomer().get(0).getEmail(), subjectFall, body);
        }

    }

}
