package org.jala.university.domain.repository;

import org.jala.university.domain.entity.PasswordRecovery;

import java.util.Optional;

public interface PasswordRecoveryRepository {

    PasswordRecovery save(PasswordRecovery passwordRecovery);

    Optional<PasswordRecovery> findByToken(String token);

    Optional<PasswordRecovery> findByCustomerId(Integer customerId);

    void delete(PasswordRecovery passwordRecovery);

    void deleteExpiredTokens();
}