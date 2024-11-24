package org.jala.university.infrastructure.persistence_card;


import org.jala.university.application.service.service_card.LimitCreditCardService;

public class AddCustomLimit {

    public static void validLimit(String cardNumber){
        LimitCreditCardService.findLimitCreditByCardNumber(cardNumber);

    }

}
