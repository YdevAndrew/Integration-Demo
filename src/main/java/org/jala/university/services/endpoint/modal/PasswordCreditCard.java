package org.jala.university.services.endpoint.modal;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "password_credit_card")
public class PasswordCreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_password_credit_card;

    @Column(name = "password_credit_card", nullable = false)
    private String password_credit_card;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_id_credit_card")
    private CreditCard fk_credit_card;


    public PasswordCreditCard(String password) {
        this.password_credit_card = password;
    }
}
