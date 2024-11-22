package org.jala.university.services.endpoint.modal;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice_backup")
public class InvoiceBackup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;

    @Column(name = "status_invoice", nullable = false)
    private boolean statusInvoice;

    @Column(name = "number_card", nullable = false)
    private String numberCard;

    @Column(name = "number_ff_installments", nullable = false)
    private int numberOfInstallments;

    @Column(name = "date_payment", nullable = false)
    private String datePayment;

    @Column(name = "purchase_date", nullable = false)
    private String purchaseDate;

    @Column(name = "invoice_value", nullable = false)
    private BigDecimal invoiceValue;



}
