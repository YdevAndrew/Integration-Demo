package org.jala.university.application.service.service_loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_account.AccountStatus;
import org.jala.university.domain.entity.entity_account.Currency;
import org.jala.university.domain.entity.entity_loan.LoanEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LoanResultsServiceTest {

    @Mock
    private LoanResultsService loanResultsService;

    private LoanEntity loanEntity;
    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        loanEntity = new LoanEntity();
        loanEntity.setId(1);

        account = new Account(1, "123456", AccountStatus.ACTIVE, new BigDecimal("5000.0"), Currency.USD, 1, "12345", LocalDate.now(), LocalDate.now());
    }

    @Test
    void testSendAmountAccount() {

        when(loanResultsService.sendAmountAccount(loanEntity)).thenReturn(account);


        Account result = loanResultsService.sendAmountAccount(loanEntity);


        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(new BigDecimal("5000.0"), result.getBalance());
        verify(loanResultsService, times(1)).sendAmountAccount(loanEntity);
    }

    @Test
    void testPayInstallment() {

        when(loanResultsService.payInstallment(loanEntity)).thenReturn(account);

        Account result = loanResultsService.payInstallment(loanEntity);


        assertNotNull(result); // Verifica que o resultado não é nulo
        assertEquals(1, result.getId()); // Verifica o ID da conta
        assertEquals(new BigDecimal("5000.0"), result.getBalance()); // Verifica o saldo da conta
        verify(loanResultsService, times(1)).payInstallment(loanEntity); // Verifica se o método foi chamado uma vez
    }
}
