package org.jala.university.application.dto;

import lombok.*;
import org.jala.university.domain.entity.AccountStatus;
import org.jala.university.domain.entity.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;

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
