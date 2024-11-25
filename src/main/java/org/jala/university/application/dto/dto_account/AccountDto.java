package org.jala.university.application.dto.dto_account;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.jala.university.domain.entity.entity_account.AccountStatus;
import org.jala.university.domain.entity.entity_account.Currency;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Integer id;
    private String accountNumber;
    private BigDecimal balance;
    private AccountStatus status;
    private Currency currency;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String paymentPassword;
}
