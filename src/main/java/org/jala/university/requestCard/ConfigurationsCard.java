package org.jala.university.requestCard;

import org.jala.university.services.endpoint.modal.PasswordCreditCard;
import org.jala.university.services.endpoint.modal.PaymentDate;
import org.jala.university.services.endpoint.services.CreditCardClientService;

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




}
