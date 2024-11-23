package org.jala.university.application.mapper.mapper_account;

import org.jala.university.domain.entity.accountEntity.Authentication;
import org.jala.university.application.dto.dto_account.AuthenticationDto;

public class AuthenticationMapper {
    public static AuthenticationDto toDto(Authentication authentication) {
        AuthenticationDto dto = new AuthenticationDto();
        dto.setId(authentication.getId());
        dto.setCpf(authentication.getCpf());
        // Adicione outros campos conforme necessário
        return dto;
    }

    public static Authentication toEntity(AuthenticationDto dto) {
        Authentication authentication = new Authentication();
        authentication.setId(dto.getId());
        authentication.setEmail(dto.getEmail());
        // Adicione outros campos conforme necessário
        return authentication;
    }
}
