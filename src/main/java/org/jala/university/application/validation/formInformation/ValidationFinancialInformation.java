package org.jala.university.application.validation.formInformation;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.jala.university.application.validation.billingAddress.ValidationFields;

public class ValidationFinancialInformation {


    public static void validAll(ToggleGroup toggleGroup, Label lblHaveCreditCard, TextField txtIncome, Label lblIncome,
                                ToggleGroup toggleGroupOcupation, Label lblOcupation, TextField txtCurrentPosition, Label lblCurrentPosition) {

        creditCardsAnswer(toggleGroup, lblHaveCreditCard);
        incomeMonth(txtIncome, lblIncome);
        occupationAnswer(toggleGroupOcupation, lblOcupation);
        currentPosition(txtCurrentPosition, lblCurrentPosition);
        toggle(toggleGroup, lblHaveCreditCard);
        toggle(toggleGroupOcupation, lblOcupation);
    }

    public static void creditCardsAnswer(ToggleGroup toggleGroup, Label lblHaveCreditCard) {
        lblHaveCreditCard.setText("");
        if (!ValidationFields.validSelect(toggleGroup)) {
            lblHaveCreditCard.setText("Select Yes or No");
        }
    }

    public static void incomeMonth(TextField txtIncome, Label lblIncome) {
        lblIncome.setText("");
        if (txtIncome.getText().isEmpty()) {
            lblIncome.setText("Income cannot be empty");
        } else if (!ValidationFields.isNumeric(txtIncome.getText()) || !ValidationFields.isNonNegative(txtIncome.getText())) {
            lblIncome.setText("Income must be a non-negative number");
        }
    }

    public static void occupationAnswer(ToggleGroup toggleGroupOcupation, Label lblOcupation) {
        lblOcupation.setText("");
        if (!ValidationFields.validSelect(toggleGroupOcupation)) {
            lblOcupation.setText("Select one option");
        }
    }

    public static void currentPosition(TextField txtCurrentPosition, Label lblCurrentPosition) {
        lblCurrentPosition.setText("");
        if (!ValidationFields.isAlphabetic(txtCurrentPosition.getText())) {
            lblCurrentPosition.setText("Current position should contain only alphabetic characters");
        }
    }

    public static boolean validValues(ToggleGroup toggleGroup, TextField txtIncome, ToggleGroup toggleGroupOcupation, TextField txtCurrentPosition) {
        return ValidationFields.validSelect(toggleGroup) &&
                ValidationFields.isNumeric(txtIncome.getText()) &&
                ValidationFields.isNonNegative(txtIncome.getText()) &&
                ValidationFields.validSelect(toggleGroupOcupation) &&
                ValidationFields.isAlphabetic(txtCurrentPosition.getText());
    }

    /***
     * Validates if there is an option selected in a ToggleGroup.
     * @param toggleGroup the ToggleGroup of options.
     * @param lblResult the Label to display failure messages.
     */
    public static void toggle(ToggleGroup toggleGroup, Label lblResult) {
        ValidationPersonalInfo.toggle(toggleGroup, lblResult);
    }
}
