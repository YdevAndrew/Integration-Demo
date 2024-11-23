package org.jala.university.application.service;

import org.jala.university.application.dto.dto_account.AccountDto;
import org.jala.university.application.service.service_account.AccountService;
import org.jala.university.application.service.service_account.AccountServiceImpl;
import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_account.AccountStatus;
import org.jala.university.domain.entity.entity_account.Currency;
import org.jala.university.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class AccountServiceImplTest {
    private AccountService accountService;
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    public void testCreateAccount() {
        // Arrange
        AccountDto accountDto = AccountDto.builder()
                .accountNumber("123456789")
                .balance(BigDecimal.ZERO)
                .status(AccountStatus.FROZEN)
                .currency(Currency.BRL)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .paymentPassword("")
                .build();

        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account account = invocation.getArgument(0);
            Account savedAccount = new Account();
            savedAccount.setId(1432); // Definindo um ID fictício
            savedAccount.setAccountNumber(account.getAccountNumber());
            savedAccount.setBalance(account.getBalance());
            savedAccount.setStatus(account.getStatus());
            savedAccount.setCurrency(account.getCurrency());
            savedAccount.setCreatedAt(account.getCreatedAt());
            savedAccount.setUpdatedAt(account.getUpdatedAt());
            savedAccount.setPaymentPassword(account.getPaymentPassword());
            savedAccount.setCustomerId(1);
            return savedAccount; // Retornando a conta populada
        });

        // Act
        AccountDto createdAccount = accountService.createAccount(accountDto, 1);

        // Assert
        assertNotNull(createdAccount);
        assertEquals("123456789", createdAccount.getAccountNumber());
        assertEquals(BigDecimal.ZERO, createdAccount.getBalance());
        assertEquals(AccountStatus.FROZEN, createdAccount.getStatus());
        assertEquals(Currency.BRL, createdAccount.getCurrency());
    }}