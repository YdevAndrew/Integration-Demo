package org.jala.university.application.dto.creditCardModule;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class LimitCreditCardDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_limit_credit_card;
    private BigDecimal limit_credit;
    private int fk_account_id;
    private int fk_card_id;

    public LimitCreditCardDTO(BigDecimal limit_credit, int fk_account_id, int fk_card_id) {
        this.limit_credit = limit_credit;
        this.fk_account_id = fk_account_id;
        this.fk_card_id = fk_card_id;
    }

    public LimitCreditCardDTO() {}
}
