package org.jala.university.application.service;

import jakarta.persistence.EntityNotFoundException;
import org.jala.university.application.dto.PasswordRecoveryDto;
import org.jala.university.application.mapper.PasswordRecoveryMapper;
import org.jala.university.domain.entity.Customer;
import org.jala.university.domain.entity.PasswordRecovery;
import org.jala.university.domain.repository.CustomerRepository;
import org.jala.university.domain.repository.PasswordRecoveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {

    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final CustomerRepository customerRepository;
    private final PasswordRecoveryMapper passwordRecoveryMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordRecoveryServiceImpl(
            PasswordRecoveryRepository passwordRecoveryRepository,
            CustomerRepository customerRepository,
            PasswordRecoveryMapper passwordRecoveryMapper,
            PasswordEncoder passwordEncoder) {
        this.passwordRecoveryRepository = passwordRecoveryRepository;
        this.customerRepository = customerRepository;
        this.passwordRecoveryMapper = passwordRecoveryMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public PasswordRecoveryDto createRecoveryToken(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // Delete any existing token for this customer
        passwordRecoveryRepository.findByCustomerId(customerId)
                .ifPresent(passwordRecoveryRepository::delete);

        // Create new token
        PasswordRecovery passwordRecovery = new PasswordRecovery();
        passwordRecovery.setCustomer(customer);
        passwordRecovery.setRecoveryToken(UUID.randomUUID().toString());
        passwordRecovery.setRequestedAt(Timestamp.from(Instant.now()));
        passwordRecovery.setExpiresAt(Timestamp.from(Instant.now().plus(24, ChronoUnit.HOURS)));

        PasswordRecovery savedRecovery = passwordRecoveryRepository.save(passwordRecovery);
        return passwordRecoveryMapper.toDto(savedRecovery);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateToken(String token) {
        return passwordRecoveryRepository.findByToken(token)
                .map(recovery -> {
                    Instant now = Instant.now();
                    return !recovery.getExpiresAt().toInstant().isBefore(now);
                })
                .orElse(false);
    }

    @Override
    @Transactional
    public void updatePassword(String token, String newPassword) {
        PasswordRecovery recovery = passwordRecoveryRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Invalid or expired token"));

        if (recovery.getExpiresAt().toInstant().isBefore(Instant.now())) {
            throw new IllegalStateException("Token has expired");
        }

        Customer customer = recovery.getCustomer();
        customer.setPassword(passwordEncoder.encode(newPassword).getBytes());
        customerRepository.save(customer);

        // Delete the used token
        passwordRecoveryRepository.delete(recovery);
    }

    @Override
    @Transactional
    public void deleteExpiredTokens() {
        passwordRecoveryRepository.deleteExpiredTokens();
    }
}
