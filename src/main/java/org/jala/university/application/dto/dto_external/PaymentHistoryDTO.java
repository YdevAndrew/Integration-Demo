package org.jala.university.application.dto.dto_external;


import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_external.StatusEntity;
import org.jala.university.domain.entity.entity_external.TransactionTypeEntity;

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
    TransactionTypeEntity transactionType;
    LocalDate expiredDate;
    String agencyReceiver;
    String accountReceiver;
    String nameReceiver;
    String bankNameReceiver;
    StatusEntity status;
}