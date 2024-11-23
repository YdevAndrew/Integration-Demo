package org.jala.university.application.dto.dto_transaction;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistoryDTO {
    BigDecimal amount;
    LocalDateTime transactionDate;
    String status;
    String transactionType;
    String description;
    String cpfReceiver;
    String agencyReceiver;
    String accountReceiver;
    String nameReceiver;
    String bankNameReceiver;
}
