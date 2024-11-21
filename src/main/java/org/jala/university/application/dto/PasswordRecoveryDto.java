package org.jala.university.application.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordRecoveryDto {
    private Integer id;
    private String recoveryToken;
    private Timestamp requestedAt;
    private Timestamp expiresAt;
    private Integer customerId;
}
