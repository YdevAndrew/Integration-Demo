package org.jala.university.application.dto.dto_card;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/***
 * representation from invoicees
 */
@Getter
@Setter

public class InvoicesDTO {

    private int id_invoices;
    private BigDecimal total_value;
    private BigDecimal total_sum;
    private boolean status_invoice;
    private String number_card;
    private int number_of_installments;
    private int fk_id_account;

    public InvoicesDTO(int id_invoices, BigDecimal total_value, BigDecimal total_sum, boolean status_invoice, String number_card, int number_of_installments, int fk_id_account) {
        this.id_invoices = id_invoices;
        this.total_value = total_value;
        this.total_sum = total_sum;
        this.status_invoice = status_invoice;
        this.number_card = number_card;
        this.number_of_installments = number_of_installments;
        this.fk_id_account = fk_id_account;
    }

    public InvoicesDTO() {}
}
