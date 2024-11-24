package org.jala.university.domain.entity.entity_card;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credit_card_client")
public class CreditCardClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_credit_card_client;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_credit_card_id")
    private CreditCard credit_card;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_password_credit_card_id")
    private PasswordCreditCard passwordCreditCard;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_date_payment")
    private PaymentDate paymentDate;

    @Column(name = "fk_account_id", nullable = false)
    private int fk_account_id;




}
