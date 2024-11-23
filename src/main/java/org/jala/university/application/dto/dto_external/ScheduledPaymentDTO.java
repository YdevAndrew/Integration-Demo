package org.jala.university.application.dto.dto_external;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.jala.university.domain.entity.entity_account.Account;

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
