package org.jala.university.application.dto.dto_card;

import lombok.Getter;
import lombok.Setter;
import org.jala.university.domain.entity.entity_card.CreditCard;

@Getter
@Setter
public class PaymentDateDTO {

    private Long id_payment_date;
    private String dayPayment;
    private CreditCard fk_credit_card;


    public PaymentDateDTO(Long id_payment_date, String dayPayment, CreditCard fk_credit_card) {
        this.id_payment_date = id_payment_date;
        this.dayPayment = dayPayment;
        this.fk_credit_card = fk_credit_card;
    }
}
