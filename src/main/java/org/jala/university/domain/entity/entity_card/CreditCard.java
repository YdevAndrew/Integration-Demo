package org.jala.university.domain.entity.entity_card;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "credit_card")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_credit_card;

    @Column(name = "number_card", nullable = false, unique = true)
    private String numberCard;

    @Column(name = "cvv", nullable = false)
    private String cvv;

    @Column(name = "expiration_date", nullable = false)
    private String expiration_date;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "name_card", nullable = false)
    private String name_card;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_limit_card_id")
    private LimitCreditCard fk_limit_card;

    @Column(name = "type_card", nullable = false)
    private String typeCard;


    public CreditCard(String numberCreditCard, String cvv, String expirationDate, boolean active, String nameCreditCard, LimitCreditCard limitCreditCard, String type) {
        this.numberCard = numberCreditCard;
        this.cvv = cvv;
        this.expiration_date = expirationDate;
        this.active = active;
        this.name_card = nameCreditCard;
        this.fk_limit_card = limitCreditCard;
        this.typeCard = type;
    }
}
