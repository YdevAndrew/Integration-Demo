package org.jala.university.domain.entity.entity_transaction;

import jakarta.persistence.*;
import lombok.*;
import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_external.ScheduledPaymentEntity;


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

    @Column(name = "cpf_cnpj_receiver", nullable = true)
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

    @ManyToOne
    @JoinColumn(name = "scheduled_payment", nullable = true)
    private ScheduledPaymentEntity scheduledPayment;

}
