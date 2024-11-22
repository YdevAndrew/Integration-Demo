package org.jala.university.services.endpoint.dto;

import lombok.Getter;
import lombok.Setter;
import org.jala.university.services.endpoint.modal.Invoices;
import org.jala.university.services.endpoint.modal.StatusInvoice;


@Getter
@Setter
public class DatePaymentDTO {
    private int id_payment;
    private StatusInvoice fk_id_status_invoices;
    private Invoices fk_id_invoice;
    private String date_payment;

    public DatePaymentDTO(StatusInvoice fk_id_status_invoices, Invoices fk_id_invoice, String date_payment) {
        this.fk_id_status_invoices = fk_id_status_invoices;
        this.fk_id_invoice = fk_id_invoice;
        this.date_payment = date_payment;
    }
}
