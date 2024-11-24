package org.jala.university.infrastructure.persistence_card.tools;

import javafx.scene.control.Label;
import org.jala.university.application.service.service_card.*;
import org.jala.university.domain.entity.entity_card.PasswordCreditCard;
import org.jala.university.domain.entity.entity_card.PaymentDate;
import org.jala.university.infrastructure.persistence_card.AddPasswordCard;


import java.math.BigDecimal;
import java.sql.SQLException;

public class ConfigurationsCard {
    public static void addOrUpdatePassword(String numberCreditCard, String password) throws SQLException {

        AddPasswordCard.validation(password);

        CreditCardClientService.updatePasswordCreditCard(numberCreditCard, new PasswordCreditCard(AddPasswordCard.getPassword()));

    }


    public static void addDatePayment(String numberCreditCard, String payment) throws SQLException {
        CreditCardClientService creditCardClientService = new CreditCardClientService();
        PaymentDate paymentDate = new PaymentDate(payment);
        creditCardClientService.updateDatePayment(numberCreditCard, paymentDate);
    }

    public static void addDatePaymentDefault(String numberCreditCard, String payment) throws SQLException {
        CreditCardClientService creditCardClientService = new CreditCardClientService();
        PaymentDate paymentDate = new PaymentDate(payment);
        creditCardClientService.updateDatePaymentDefault(numberCreditCard, paymentDate);

    }

    public static void updatePersonalLimit(String numberCreditCard, BigDecimal value) {
        AddCustomLimitService.validateAndSaveOrUpdateCustomLimit(numberCreditCard, value);

    }

    public static void main(String[] args) throws SQLException {
        //addDatePayment("3978152532135507","28");
       // System.out.println(CreditCardClientService.updatePaymentIsAvaliable("7569541641198859"));

        //System.out.println(checkPassord("123456", "5618552034641541"));

        //System.out.println(deleterCard("6446551387892145","123456"));
    }


    public static boolean validIfCardHasPassowrd(String numberCard, Label erroMessage) {
        boolean isValid = false;
        if (PasswordCreditCardService.hasPassword(numberCard)) {
            isValid = true;
        } else {
            messageError(erroMessage, 1);
        }
        return isValid;
    }


    public static boolean checkPassord(String passordForm, String numberCard) {
        PasswordCreditCard passwordCreditCard = PasswordCreditCardService.findPasswordByCardNumber(numberCard);
        assert passwordCreditCard != null;
        if (AddPasswordCard.checkPassword(passordForm, passwordCreditCard.getPassword_credit_card())) {
            return true;
        }
        return false;

    }

    public static boolean deleterCard(String numberCard, String passordForm, Label erroMessage) {
        boolean isValid = false;

        if (validIfCardHasPassowrd(numberCard, erroMessage)) {
            if (checkPassord(passordForm, numberCard)) {
                if (!InvoicesService.checkIfHaveInvoicesInOpen(numberCard)) {
                    DeleteVirtualCreditCard.deleteCard(numberCard);
                    isValid = true;
                } else {
                    messageError(erroMessage, 2);
                }
            } else {
                messageError(erroMessage, 3);
            }
        } else {
            messageError(erroMessage, 1);
        }

        return isValid;
    }


    public static void messageError(Label labelError, int code){
        if(code == 1) {
            labelError.setText("You need to put a password in the credit card first");
        }else if(code == 2) {
            labelError.setText("You have open invoices");
        }else if(code == 3) {
            labelError.setText("Password wrong");
        }
    }



}
