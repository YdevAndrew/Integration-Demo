package org.jala.university.services.endpoint.dto;

import lombok.Getter;
import lombok.Setter;
import org.jala.university.services.endpoint.modal.StatusInvoice;

import java.math.BigDecimal;
@Getter
@Setter

public class StatusInvoiceDTO {

    private int id_status_invoice;
    private boolean status_invoice;
    private StatusInvoice fk_id_status_invoice;
    private BigDecimal value_invoice;

    public StatusInvoiceDTO(boolean status_invoice, StatusInvoice fk_id_status_invoice, BigDecimal value_invoice) {
        this.status_invoice = status_invoice;
        this.fk_id_status_invoice = fk_id_status_invoice;
        this.value_invoice = value_invoice;
    }

    public StatusInvoiceDTO() {}
}
