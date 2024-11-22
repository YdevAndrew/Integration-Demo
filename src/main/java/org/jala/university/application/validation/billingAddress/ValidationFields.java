package org.jala.university.application.validation.billingAddress;

import javafx.scene.control.ToggleGroup;
/***
 * Class responsible for validating the filled data
 */

public class ValidationFields {

    /***
     * validates if the field is numeric
     * @param text
     * @return boolean
     */

    public static boolean isNumeric(String text) {
        return text != null && text.matches("\\d+");
    }


    /***
     * validates if the field is alphabetic
     * @param text
     * @return true or false
     */
    public static boolean isAlphabetic(String text) {
        return text != null && text.matches("[a-zA-Z]+");
    }

    /***
     * validates if the field is a positive number
     * @param text
     * @return true or false
     */
    public static boolean isNonNegative(String text) {
        if (text == null || text.isEmpty()) return false;
        try {
            int number = Integer.parseInt(text);
            return number >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /***
     * validate cep
     * @param text
     * @return true or false
     */
    public static boolean validCEP(String text){
        return isNumeric(text) && isNonNegative(text) && text.length() == 8;
    }

    /***
     * validate if text has only characters
     * @param text
     * @return true or false
     */
    public static boolean validCaracther(String text) {
        return text != null && !text.trim().isEmpty() && text.matches("^[a-zA-ZÀ-ÿ\\s'-]+$");
    }


    /***
     * validate if togglegroup has a selected field
     * @param toggleGroup
     * @return true or false
     */
    public static boolean validSelect(ToggleGroup toggleGroup) {
        return toggleGroup != null && toggleGroup.getSelectedToggle() != null;
    }
}
