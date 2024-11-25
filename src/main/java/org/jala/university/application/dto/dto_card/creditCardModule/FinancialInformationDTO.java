package org.jala.university.application.dto.dto_card.creditCardModule;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FinancialInformationDTO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id_financial_information;
    private boolean have_credit_card;
    private BigDecimal month_income;
    private String type_ocupation;
    private String current_position;
    private String marital_status;
    private int fk_account_id;

    public FinancialInformationDTO(boolean have_credit_card, BigDecimal month_income, String type_ocupation, String current_position, String marital_status) {
        this.have_credit_card = have_credit_card;
        this.month_income = month_income;
        this.type_ocupation = type_ocupation;
        this.current_position = current_position;
        this.marital_status = marital_status;
    }

    public FinancialInformationDTO() {}
}
