package org.jala.university.requestCard;

import lombok.Getter;
import lombok.Setter;
import org.jala.university.services.endpoint.services.CreditCardService;

import java.util.Random;

public class CreateCVV {

    @Getter @Setter
    private static String cvv;

    public synchronized static void generateCvv(){
        setCvv(validateCvv());

    }

    public static String validateCvv(){
        String cvv;
        do {
            cvv = creationRandomCVV();
        } while (ifCvvExists(cvv));

        return cvv;
    }


    public static String creationRandomCVV(){
        Random random = new Random();
        int randomNumber = random.nextInt(900) + 100;
        return String.valueOf(randomNumber);
    }

    public static boolean ifCvvExists(String cvv){
        CreditCardService creditCardService = new CreditCardService();

        if(creditCardService.getCvvCreditCard(cvv) != null){
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {
        generateCvv();
        System.out.println(getCvv());
    }
}
