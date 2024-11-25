package org.jala.university.domain.entity.entity_card;


/***
 * Class responsible for credit card limit tools
 */
public class LimitCreditCardTool {

    /***
     *
     * @param monthIncome is the month income from customer bank
     * @param percentage is the value to calculate the limit
     * @return the client limit
     */
    public static double calculateLimit(String monthIncome, double percentage){
        double income = Double.parseDouble(monthIncome);

        return (income * percentage) / 100;
    }

    /***
     *
     * @param limit is the client limit calculate by function calculateLimit
     * @param valueMin is the minimum value that the bank sets to accept as a limit
     * @return if the limit is acceptable
     */
    public static boolean validLimit(double limit, double valueMin){
        return !(limit <= valueMin);
    }

    /***
     * function responsible to validate if the user can get a limit
     * @param valueMin is the minimum value that the bank sets to accept as a limit
     * @param monthIncome is the month income from customer bank
     * @param percentage is the value to calculate the limit
     * @return true or false
     */
    public static boolean calculate(String monthIncome, double percentage, double valueMin){
        return validLimit(calculateLimit(monthIncome, percentage), valueMin);
    }
}
