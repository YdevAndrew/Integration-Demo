package org.jala.university.services.endpoint.modal;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "date_payment")
public class DatePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_payment;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_id_status_invoices_id")
    private StatusInvoice fk_id_status_invoices;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_id_invoice_id")
    private Invoices fk_id_invoice;

    @Column(name = "date_payment", nullable = false)
    private String date_payment;


    public DatePayment(StatusInvoice statusInvoice, Invoices invoice, String string) {
        this.fk_id_status_invoices = statusInvoice;
        this.fk_id_invoice = invoice;
        this.date_payment = string;
    }
}
