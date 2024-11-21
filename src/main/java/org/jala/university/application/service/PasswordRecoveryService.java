package org.jala.university.application.service;

import org.jala.university.application.dto.PasswordRecoveryDto;

public interface PasswordRecoveryService {
    PasswordRecoveryDto createRecoveryToken(Integer customerId);
    boolean validateToken(String token);
    void updatePassword(String token, String newPassword);
    void deleteExpiredTokens();
}
