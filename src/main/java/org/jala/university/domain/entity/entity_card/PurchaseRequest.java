package org.jala.university.domain.entity.entity_card;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
/***
 * represention from the purchase
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchaseRequest")
public class PurchaseRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_purchase_request;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_credit_card_id")
    private CreditCard creditCard;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_seller_credit_card_id")
    private CreditCard sellerCreditCard;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_product_id")
    private Product product;

    @Column(name = "number_of_installments", nullable = false)
    private int numberOfInstallments;

    @Column(name = "date_buy", nullable = false)
    private String dateBuy;



}
