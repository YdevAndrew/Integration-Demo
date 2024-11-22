package org.jala.university.services.endpoint.modal;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/***
 * representation from invoicees
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoices")
public class Invoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_invoices;

    @Column(name = "total_value", nullable = false)
    private BigDecimal total_value;


    @Column(name = "status_invoice", nullable = false)
    private boolean status_invoice;

    @Column(name = "number_card", nullable = false)
    private String number_card;

    @Column(name = "number_of_installments", nullable = false)
    private int number_of_installments;

    @Column(name = "fk_id_account", nullable = false)
    private int fk_id_account;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "credit_card_id_credit_card")
    private CreditCard credit_card;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_card_seller")
    private CreditCard credit_card_seller;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_product")
    private Product fk_product;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_purchase_request")
    private PurchaseRequest fk_purchase_request;


    public Invoices(BigDecimal totalValue, boolean status_invoice, String creditCardNumber, int numberOfInstallments, int fk_id_account, CreditCard creditCard, CreditCard cardSeller, Product product, PurchaseRequest purchaseRequest) {
    this.total_value = totalValue;
    this.status_invoice = status_invoice;
    this.number_card = creditCardNumber;
    this.number_of_installments = numberOfInstallments;
    this.fk_id_account = fk_id_account;
    this.credit_card = creditCard;
    this.credit_card_seller = cardSeller;
    this.fk_product = product;
    this.fk_purchase_request = purchaseRequest;
    }
}
