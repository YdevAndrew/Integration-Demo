package org.jala.university.infrastructure.persistence_card;


import org.jala.university.application.service.service_card.CreditCardService;
import org.jala.university.domain.entity.entity_card.CreditCard;

public class ActiveCreditCard {

    public static CreditCard checkStatus(CreditCard card) {
        try {
            return CreditCardService.getStatusCard(card.getId_credit_card());

        }catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
      if(checkStatus(CreditCardService.getCreditCardByNumberCard("3878687640558653")).isActive()) {
            System.out.println("true");
        }else{
          System.out.println("false");
      }
    }

    public static void activateCreditCard(String cardNumber ) {



       CreditCard card = CreditCardService.getCreditCardByNumberCard(cardNumber);

        CreditCardService creditCardService = new CreditCardService();
        CreditCard currentStatusCard = checkStatus(card);

        if (currentStatusCard != null && !currentStatusCard.isActive()) {
            creditCardService.activateCreditCard(card.getId_credit_card());
        } else {
            System.out.println("Error");
        }
    }

    public static void desactiveCreditCard(String cardNumber) {
        CreditCardService creditCardService = new CreditCardService();
        CreditCard card = CreditCardService.getCreditCardByNumberCard(cardNumber);

        CreditCard currentStatusCard = checkStatus(card);

        if (currentStatusCard != null && currentStatusCard.isActive()) {
            CreditCardService.deactivateCreditCard(card.getId_credit_card());
        } else {
            System.out.println("Error");
        }
    }



}
