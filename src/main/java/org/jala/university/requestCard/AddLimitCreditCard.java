package org.jala.university.requestCard;


import lombok.Getter;
import lombok.Setter;
import org.jala.university.application.object.InforrmationForm.LimitCreditCardTool;

public class AddLimitCreditCard {
    @Getter @Setter
    private static double limit;


    public static void addlimit(String txtIncome){
        double value = LimitCreditCardTool.calculateLimit(txtIncome, 25);
        if(value <= 5000){
            setLimit(value);
        }else{
            setLimit(5000);
        }
    }

    public static boolean validationValue(String txtIncome) {
        boolean isValid = false;

        if (LimitCreditCardTool.calculate(txtIncome, 25, 50)) {
            addlimit(txtIncome);
            isValid =  true;
        }

        return isValid;

    }



}
