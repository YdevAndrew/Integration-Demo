package org.jala.university.infrastructure.persistence_card;

import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

public class AddPasswordCard {
    @Getter
    @Setter
    private static String password;


    public static boolean validation(String password) {
        boolean isValid = false;
        if (AddPasswordCard.validPassword(password)) {
            String hashedPassword = AddPasswordCard.hashPassword(password);
            boolean isNumeric = AddPasswordCard.isNumericPassword(password);
            if(isNumeric){
                setPassword(hashedPassword);
                isValid = true;
            }
        } else {
            System.out.println("Invalid password!");
            isValid = false;
        }
        return isValid;
    }


    public static boolean validationWithLabel(String password, Label label) {
        boolean isValid = false;
        if (AddPasswordCard.validPassword(password)) {
            String hashedPassword = AddPasswordCard.hashPassword(password);
            boolean isNumeric = AddPasswordCard.isNumericPassword(password);
            if(isNumeric){
                setPassword(hashedPassword);
                isValid = true;
            }
        } else {
            label.setText("Invalid password, less than 6!");
            isValid = false;
        }
        return isValid;
    }

    public static void makeLabel(String password, Label labelError) {
        if(!validationWithLabel(password, labelError)){
            labelError.setText("Invalid password, less than 6!");
        }else {
            labelError.setText("");
        }
    }


    public static boolean validPassword(String password) {
        if (password != null && password.length() == 6) {
            return true;
        }
        return false;
    }


    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }


    public static boolean checkPassword(String originalPassword, String hashedPassword) {
        return BCrypt.checkpw(originalPassword, hashedPassword);
    }


    public static boolean isNumericPassword(String password) {
        return password.matches("[0-9]+");
    }






}
