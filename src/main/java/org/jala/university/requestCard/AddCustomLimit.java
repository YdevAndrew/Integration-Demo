package org.jala.university.requestCard;

import org.jala.university.services.endpoint.services.LimitCreditCardService;

public class AddCustomLimit {

    public static void validLimit(String cardNumber){
        LimitCreditCardService.findLimitCreditByCardNumber(cardNumber);

    }

}
