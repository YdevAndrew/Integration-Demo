package org.jala.university.utils.utils_card.formInformation;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.jala.university.utils.utils_card.billingAddress.ValidationFields;

/***
 * Class responsible for calling the ValidationFields class and returning the response in the form labels
 */
public class ValidationPersonalInfo {


    /***
     * all the parameters here are the fields that the customer's bank has placed in the form, this function calls another function
     * to see if everything is valid
     */
    public static void validationAll(TextField txtCEP, Label lblCEPResult, TextField txtStreet, Label lblStreetResult, TextField txtCity, Label lblCityResult,
                                     TextField txtState, Label lblStateResult, TextField txtCountry, Label lblCountryResult, RadioButton rbMarried, RadioButton rbDowager, RadioButton rbOther, ToggleGroup toggleGroup, Label lblResultMarried) {
        cleanField(lblCEPResult, lblStreetResult, lblCityResult, lblStateResult, lblCountryResult);
        validCEP(txtCEP, lblCEPResult);
        validStreet(txtStreet, lblStreetResult);
        validCity(txtCity, lblCityResult);
        validState(txtState, lblStateResult);
        validCountry(txtCountry, lblCountryResult);
        toggle(toggleGroup, lblResultMarried);
    }

    /***
     * function responsible for validating cep
     * @param txtCEP is the field the bank customer puts in the form
     * @param lblCEPResult is the label where a failure message is hidden
     */
    public static void validCEP(TextField txtCEP, Label lblCEPResult){
        lblCEPResult.setText("");
        if(!ValidationFields.validCEP(txtCEP.getText())) {
            lblCEPResult.setText("CEP is invalid");
        }
    }

    /***
     * @param txtStreet is the field the bank customer puts in the form
     * @param lblStreetResult is the label where a failure message is hidden
     */
    public static void validStreet(TextField txtStreet, Label lblStreetResult){
        lblStreetResult.setText("");
        if(!ValidationFields.validCaracther(txtStreet.getText())){
            lblStreetResult.setText("Street is invalid");
        }
    }

    /***
     * @param txtCity is the field the bank customer puts in the form
     * @param lblCityResult is the label where a failure message is hidden
     */
    public static void validCity(TextField txtCity, Label lblCityResult){
        lblCityResult.setText("");
        if(!ValidationFields.validCaracther(txtCity.getText())) {
            lblCityResult.setText("City is invalid");
        }
    }

    /***
     * @param txtSate is the field the bank customer puts in the form
     * @param lblSateResult is the label where a failure message is hidden
     */
    public static void validState(TextField txtSate, Label lblSateResult){
        lblSateResult.setText("");
        if(!ValidationFields.validCaracther(txtSate.getText())) {
            lblSateResult.setText("State is invalid");
        }
    }

    /***
     * @param txtCountry is the field the bank customer puts in the form
     * @param lblCountryResult is the label where a failure message is hidden
     */
    public static void validCountry(TextField txtCountry, Label lblCountryResult){
        lblCountryResult.setText("");
        if(!ValidationFields.validCaracther(txtCountry.getText())) {
            lblCountryResult.setText("Country is invalid");
        }
    }

    /***
     * all the parameters here are the labels from the form pages
     */
    public static void cleanField(Label lblCEPResult, Label lblStreetResult, Label lblCityResult, Label lblStateResult, Label lblCountryResult){
        lblCEPResult.setText("");
        lblStreetResult.setText("");
        lblCityResult.setText("");
        lblStateResult.setText("");
        lblCountryResult.setText("");
    }

    /***
     * all the parameters here are the fields that the customer's bank has placed in the form, this function calls another function
     * to see if everything is valid
     * @return true of false
     */
    public static boolean validationValues(TextField txtCEP, TextField txtStreet, TextField txtCity,
                                     TextField txtState, TextField txtCountry,ToggleGroup toggleGroup  ){

        boolean isValid = true;

        if(!ValidationFields.validCEP(txtCEP.getText()) || !ValidationFields.validCaracther(txtStreet.getText()) || !ValidationFields.validCaracther(txtCity.getText()) ||
                !ValidationFields.validCaracther(txtState.getText()) || !ValidationFields.validCaracther(txtCountry.getText()) || !ValidationFields.validSelect(toggleGroup)) {
            isValid = false;
        }
        return isValid;
    }

    /***
     * function responsible to validate if there is one element selected
     * @param toggleGroup is the group from options where customer bank select
     * @param lblResultMarried is the label where a failure message is hidden
     */
    public static void toggle(ToggleGroup toggleGroup, Label lblResultMarried) {
        lblResultMarried.setText("");
        if (!ValidationFields.validSelect(toggleGroup)) {
            lblResultMarried.setText("Select one ");
        }
    }

}
