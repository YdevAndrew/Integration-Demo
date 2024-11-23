package org.jala.university.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment_history")
public class PaymentHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusEntity status;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id")
    private TransactionTypeEntity transactionType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "cpf_cnpj_receiver")
    private String cpfReceiver;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "description")
    private String description;

    @Column(name = "agency_receiver")
    private String agencyReceiver;

    @Column(name = "account_receiver")
    private String accountReceiver;

    @Column(name = "name_receiver")
    private String nameReceiver;

    @Column(name = "bank_name_receiver")
    private String bankNameReceiver;

}
