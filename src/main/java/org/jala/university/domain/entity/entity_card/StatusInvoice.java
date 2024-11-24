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
@Table(name = "status_invoice")
public class StatusInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_status_invoice;

    @Column(name = "status_invoice", nullable = false)
    private boolean status_invoice;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_id_invoice")
    private Invoices fk_id_invoice;

    @Column(name = "value_invoice", nullable = false)
    private BigDecimal value_invoice;


    public StatusInvoice(boolean status_invoice, Invoices invoice, BigDecimal installmentValue) {
        this.status_invoice = status_invoice;
        this.fk_id_invoice = invoice;
        this.value_invoice = installmentValue;
    }
}
