package org.jala.university.domain.entity.entity_external;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.jala.university.domain.entity.entity_account.Account;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment_history")
public class ExternalPaymentHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column (name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private ExternalTransactionTypeEntity transactionType;

    @Column(name = "expired_date", nullable = false)
    private LocalDate expiredDate;

    @Column(name = "cpfReceiver")
    private String cpfReceiver;

    @Column(name = "cnpjReceiver")
    private String cnpjReceiver;

    @Column(name = "agency_receiver", nullable = false)
    private String agencyReceiver;

    @Column(name = "account_receiver", nullable = false)
    private String accountReceiver;

    @Column(name = "name_receiver", nullable = false)
    private String nameReceiver;

    @Column(name = "bank_name_receiver", nullable = false)
    private String bankNameReceiver;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private ExternalStatusEntity status;

    @ManyToOne
    @JoinColumn(name = "scheduled_payment")
    private ScheduledPaymentEntity scheduledPayment;

}