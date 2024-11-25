package org.jala.university.domain.entity.entity_card;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_date")
public class PaymentDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_payment_date;

    @Column(name = "day_payment", nullable = false)
    private String dayPayment;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_credit_card")
    private CreditCard fk_credit_card;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;


    public PaymentDate(String payment) {
        this.dayPayment = payment;
    }

    public PaymentDate(String dayPayment, CreditCard creditCard) {
        this.dayPayment = dayPayment;
        this.fk_credit_card = creditCard;
    }
}
