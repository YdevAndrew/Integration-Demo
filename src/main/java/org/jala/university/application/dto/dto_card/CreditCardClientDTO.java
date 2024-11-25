package org.jala.university.application.dto.dto_card;

import lombok.Getter;
import lombok.Setter;
import org.jala.university.domain.entity.entity_card.CreditCard;
import org.jala.university.domain.entity.entity_card.LimitCreditCard;
import org.jala.university.domain.entity.entity_card.PasswordCreditCard;


@Getter
@Setter

public class CreditCardClientDTO {

    private int id_credit_card;
    private CreditCard credit_card;
    private PasswordCreditCard passwordCreditCard;
    private LimitCreditCard limitCreditCard;
    private int fk_account_id;



}
