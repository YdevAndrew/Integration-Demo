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
@Table(name = "scheduled_payment")
public class ScheduledPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "cpfReceiver")
    private String cpfReceiver;

    @Column(name = "cnpjReceiver")
    private String cnpjReceiver;

    @Column(name = "account_receiver", nullable = false)
    private String accountReceiver;

    @Column(name = "agency_receiver", nullable = false)
    private String agencyReceiver;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "expired_date", nullable = false)
    private LocalDate expiredDate;



//    @Column(nullable = false)
//    private String status;

}



