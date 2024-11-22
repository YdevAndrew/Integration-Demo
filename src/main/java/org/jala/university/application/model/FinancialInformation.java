package org.jala.university.application.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "financial_information")
public class FinancialInformation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id_financial_information;
    private boolean credit_status;
    private BigDecimal month_income;
    private String type_ocupation;
    private String current_position;
    private String marital_status;
    private int fk_account_id;

    public FinancialInformation(boolean credit_status, BigDecimal month_income, String type_ocupation, String current_position, String marital_status) {
        this.credit_status = credit_status;
        this.month_income = month_income;
        this.type_ocupation = type_ocupation;
        this.current_position = current_position;
        this.marital_status = marital_status;
    }

    public FinancialInformation() {}
}

