package org.jala.university.application.dto.dto_external;


import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_external.ExternalStatusEntity;
import org.jala.university.domain.entity.entity_external.ExternalTransactionTypeEntity;

@Data
@Builder
@Value
public class PaymentHistoryDTO {
    Integer id;
    Account accountId;
    BigDecimal amount;
    String cpfReceiver; //not null (escolher entre cpf e cnpj através da quantidade inserida)
    String cnpjReceiver; //not null (escolher entre cpf e cnpj através da quantidade inserida)
    LocalDateTime transactionDate;
    String description;
    ExternalTransactionTypeEntity transactionType;
    LocalDate expiredDate;
    String agencyReceiver;
    String accountReceiver;
    String nameReceiver;
    String bankNameReceiver;
    ExternalStatusEntity status;
}