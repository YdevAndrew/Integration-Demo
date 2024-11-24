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
@Table(name = "limit_credit_card")
public class LimitCreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_limit_credit_card;

    @Column(name = "limit_credit", nullable = false)
    private BigDecimal limit_credit;

    @Column(name = "fk_account_id", nullable = false)
    private int fk_account_id;


    public LimitCreditCard(BigDecimal limitCard, int fk_account_id) {
        this.limit_credit = limitCard;
        this.fk_account_id = fk_account_id;
    }
}
