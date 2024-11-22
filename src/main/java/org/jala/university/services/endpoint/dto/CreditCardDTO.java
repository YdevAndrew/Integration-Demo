package org.jala.university.services.endpoint.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreditCardDTO {

    private int id_credit_card;
    private String number_card;
    private String cvv;
    private String expiration_date;
    private boolean active;
    private String name_card;
    private BigDecimal limit_card;

    public CreditCardDTO(int id_credit_card, String number_card, String cvv, String expiration_date, boolean active, String name_card, BigDecimal limit_card) {
        this.id_credit_card = id_credit_card;
        this.number_card = number_card;
        this.cvv = cvv;
        this.expiration_date = expiration_date;
        this.active = active;
        this.name_card = name_card;
        this.limit_card = limit_card;
    }
}
