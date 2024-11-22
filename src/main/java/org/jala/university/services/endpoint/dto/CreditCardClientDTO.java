package org.jala.university.services.endpoint.dto;

import lombok.Getter;
import lombok.Setter;
import org.jala.university.services.endpoint.modal.CreditCard;
import org.jala.university.services.endpoint.modal.LimitCreditCard;
import org.jala.university.services.endpoint.modal.PasswordCreditCard;

@Getter
@Setter

public class CreditCardClientDTO {

    private int id_credit_card;
    private CreditCard credit_card;
    private PasswordCreditCard passwordCreditCard;
    private LimitCreditCard limitCreditCard;
    private int fk_account_id;



}
