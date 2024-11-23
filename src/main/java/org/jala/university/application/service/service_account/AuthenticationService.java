package org.jala.university.application.service.service_account;

import org.jala.university.domain.entity.accountEntity.Authentication;

import java.util.Optional;

public interface AuthenticationService {
    Authentication register(Authentication authentication);
    Optional<Authentication> getAuthentication(Integer id);
    //Optional<Authentication> getAuthenticationByEmail(String email);
    void deleteAuthentication(Integer id);

    boolean authenticate(String cpf, String password);

    void resetPassword(String email);

    void register(Integer id, String text);
}
