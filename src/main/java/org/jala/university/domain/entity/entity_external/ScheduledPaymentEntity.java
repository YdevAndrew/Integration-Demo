package org.jala.university.domain.entity.entity_external;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
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

    @Column(name = "cpf_receiver", nullable = true)
    private String cpfReceiver;

    @Column(name = "cnpj_receiver", nullable = true)
    private String cnpjReceiver;

    @Column(name = "account_recipient", nullable = false)
    private String accountReceiver;

    @Column(name = "agency_recipient", nullable = false)
    private String agencyReceiver;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @Column(name = "expired_date", nullable = false)
    private String expiredDate;




//    @Column(nullable = false)
//    private String status;

}



