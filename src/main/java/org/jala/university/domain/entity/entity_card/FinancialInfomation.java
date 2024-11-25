package org.jala.university.domain.entity.entity_card;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
/***
 * Class responsible for enabling the creation of an object with the field that the user places on the personal form page
 */
public class FinancialInfomation {

    private String creditStatus, monthIncome, ocupationStatus, currentPosition;


    public FinancialInfomation(String creditStatus, String monthIncome, String ocupationStatus, String currentPosition) {
        this.creditStatus = creditStatus;
        this.monthIncome = monthIncome;
        this.ocupationStatus = ocupationStatus;
        this.currentPosition = currentPosition;
    }

    @Override
    public String toString() {
        return "FinancialInfomation{" +
                "creditStatus='" + creditStatus + '\'' +
                ", monthIncome='" + monthIncome + '\'' +
                ", ocupationStatus='" + ocupationStatus + '\'' +
                ", currentPosition='" + currentPosition + '\'' +
                '}';
    }
}
