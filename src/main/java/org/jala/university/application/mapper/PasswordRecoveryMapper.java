package org.jala.university.application.mapper;

import org.jala.university.application.dto.PasswordRecoveryDto;
import org.jala.university.domain.entity.PasswordRecovery;
import org.springframework.stereotype.Component;

@Component
public class PasswordRecoveryMapper {

    public PasswordRecoveryDto toDto(PasswordRecovery passwordRecovery) {
        PasswordRecoveryDto dto = new PasswordRecoveryDto();
        dto.setId(passwordRecovery.getId());
        dto.setRecoveryToken(passwordRecovery.getRecoveryToken());
        dto.setRequestedAt(passwordRecovery.getRequestedAt());
        dto.setExpiresAt(passwordRecovery.getExpiresAt());
        dto.setCustomerId(passwordRecovery.getCustomer().getId());
        return dto;
    }

    public PasswordRecovery toEntity(PasswordRecoveryDto dto) {
        PasswordRecovery passwordRecovery = new PasswordRecovery();
        passwordRecovery.setRecoveryToken(dto.getRecoveryToken());
        passwordRecovery.setRequestedAt(dto.getRequestedAt());
        passwordRecovery.setExpiresAt(dto.getExpiresAt());
        passwordRecovery.setId(dto.getId());
        return passwordRecovery;
    }
}