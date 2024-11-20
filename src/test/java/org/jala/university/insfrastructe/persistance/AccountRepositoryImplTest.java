package org.jala.university.insfrastructe.persistance;

import org.jala.university.MainApp;
import org.jala.university.domain.entity.Account;
import org.jala.university.domain.entity.AccountStatus;
import org.jala.university.domain.entity.Currency;
import org.jala.university.infrastructure.persistance.AccountRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MainApp.class)
@ExtendWith(SpringExtension.class)
@Transactional
public class AccountRepositoryImplTest {

    @Autowired
    private AccountRepositoryImpl accountRepository;

    @Test
    public void testSaveAndFindById() {
        Account account = new Account();
        account.setAccountNumber("123456");
        account.setBalance(BigDecimal.valueOf(1000.00));
        account.setStatus(AccountStatus.ACTIVE);
        account.setCurrency(Currency.USD);
        account.setPaymentPassword("securePassword123");
        account.setCustomerId(1);  // Define o valor de customerId

        // Salva a conta no banco de dados
        Account savedAccount = accountRepository.save(account);

        // Encontra a conta pelo ID
        Optional<Account> foundAccount = accountRepository.findById(savedAccount.getId());

        // Assertions
        assertTrue(foundAccount.isPresent());
        assertEquals(savedAccount.getId(), foundAccount.get().getId());
        assertEquals("123456", foundAccount.get().getAccountNumber());
        assertEquals(BigDecimal.valueOf(1000.00), foundAccount.get().getBalance());
        assertEquals(AccountStatus.ACTIVE, foundAccount.get().getStatus());
        assertEquals(Currency.USD, foundAccount.get().getCurrency());
        assertEquals("securePassword123", foundAccount.get().getPaymentPassword());
        assertEquals(1, foundAccount.get().getCustomerId());  // Validação de customerId
    }}
