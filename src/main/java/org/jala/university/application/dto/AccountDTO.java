package org.jala.university.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


import org.jala.university.domain.entity.CustomerEntity;

@Data
@Builder
@Value
public class AccountDTO {
    Integer id;
    String accountNumber;
    BigDecimal balance;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String paymentPassword;
    CustomerEntity customer;

}


