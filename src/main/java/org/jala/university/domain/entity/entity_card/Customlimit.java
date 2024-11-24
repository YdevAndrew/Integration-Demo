package org.jala.university.domain.entity.entity_card;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "custom_limit")
public class Customlimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_custom_limit;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_limit_card_id")
    private LimitCreditCard fk_limit_card;

    @Column(name = "custom_limit", nullable = false)
    private BigDecimal custom_limit;


    public Customlimit(LimitCreditCard limitCreditCard, BigDecimal customLimit) {
        this.fk_limit_card = limitCreditCard;
        this.custom_limit = customLimit;
    }
}
