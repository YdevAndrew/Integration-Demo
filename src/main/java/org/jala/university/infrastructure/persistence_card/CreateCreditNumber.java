package org.jala.university.infrastructure.persistence_card;

import lombok.Getter;
import lombok.Setter;
import org.jala.university.application.service.service_card.CreditCardService;


import java.util.Random;

public class CreateCreditNumber {
    @Getter @Setter
    private static String numerCreditCard;


    public static String generateValidCardNumber() {
        String cardNumber;
        do {
            cardNumber = joinNumbers();
        } while (!isValidLuhn(cardNumber) || ifNumberExist(cardNumber));

        return cardNumber;
    }

    public static boolean isValidLuhn(String cardNumber) {
        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(cardNumber.charAt(i));

            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    public static String creationRandomNumbers() {
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        return String.valueOf(randomNumber);
    }

    public static String joinNumbers() {
        StringBuilder valueComplete = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            valueComplete.append(creationRandomNumbers());
        }

        return valueComplete.toString();
    }


    public static void generateCreditNumber(){
        setNumerCreditCard(generateValidCardNumber());

    }
    public static void main(String[] args) {


        generateCreditNumber();

        System.out.println(getNumerCreditCard());






    }



    public static boolean ifNumberExist(String number) {
        CreditCardService creditCardService = new CreditCardService();
        try {
            if (creditCardService.getCreditCardByNumberCard(number) != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


}
