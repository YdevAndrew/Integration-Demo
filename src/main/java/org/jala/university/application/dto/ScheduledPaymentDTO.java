package org.jala.university.application.dto;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


import org.jala.university.domain.entity.Account;
//import org.jala.university.domain.entity.AccountEntity;

@Data
@Builder
@Value
public class ScheduledPaymentDTO {
    Integer id;
    Account account;
    String description;
    BigDecimal amount;
    String cpfReceiver;
    String cnpjReceiver;
    String accountReceiver;
    String agencyReceiver;
    LocalDate expiredDate;
    LocalDate startDate;
    LocalDate endDate;
    LocalDateTime transactionDate;
}
