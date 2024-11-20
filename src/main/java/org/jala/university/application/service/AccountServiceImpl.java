package org.jala.university.application.service;

import org.jala.university.application.dto.AccountDto;
import org.jala.university.domain.entity.Account;
import org.jala.university.domain.entity.AccountStatus;
import org.jala.university.domain.entity.Currency;
import org.jala.university.domain.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto, Integer customerId) {
        Account account = new Account();
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setBalance(accountDto.getBalance());
        account.setStatus(accountDto.getStatus());
        account.setCurrency(accountDto.getCurrency());
        account.setCreatedAt(LocalDate.now());
        account.setUpdatedAt(LocalDate.now());
        account.setPaymentPassword(accountDto.getPaymentPassword());
        account.setCustomerId(customerId); // Set the customer ID

        Account savedAccount = accountRepository.save(account);

        return new AccountDto(
                savedAccount.getId(),
                savedAccount.getAccountNumber(),
                savedAccount.getBalance(),
                savedAccount.getStatus(),
                savedAccount.getCurrency(),
                savedAccount.getCreatedAt(),
                savedAccount.getUpdatedAt(),
                savedAccount.getPaymentPassword()
        );
    }

    @Override
    public AccountDto getAccount(Integer customerId) {
        Optional<Account> account = accountRepository.findById(customerId);
        return account.map(a -> new AccountDto(
                a.getId(),
                a.getAccountNumber(),
                a.getBalance(),
                a.getStatus(),
                a.getCurrency(),
                a.getCreatedAt(),
                a.getUpdatedAt(),
                a.getPaymentPassword())
        ).orElse(null);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(a -> new AccountDto(
                        a.getId(),
                        a.getAccountNumber(),
                        a.getBalance(),
                        a.getStatus(),
                        a.getCurrency(),
                        a.getCreatedAt(),
                        a.getUpdatedAt(),
                        a.getPaymentPassword())
                )
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Integer id) {
        accountRepository.deleteById(id); // Deleta a conta pelo ID
    }

    @Override
    public void removeAccount(AccountDto accountDto) {
        // Deleta usando o ID do AccountDto
        accountRepository.deleteById(accountDto.getId());
    }

    @Override
    public void updateAccount(AccountDto accountDto) {
        // Busca a conta existente
        Optional<Account> accountOpt = accountRepository.findById(accountDto.getId());
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.setAccountNumber(accountDto.getAccountNumber());
            account.setBalance(accountDto.getBalance());
            account.setStatus(accountDto.getStatus());
            account.setCurrency(accountDto.getCurrency());

            // Salva as alterações no banco
            accountRepository.save(account);
        }
    }

    public String generateAccountNumber() {
        // Generate a random 10-digit account number
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();

        // First 4 digits - agency number (fixed as example)
        accountNumber.append("0001");

        // Add a separator
        accountNumber.append("-");

        // Generate remaining 6 digits
        for (int i = 0; i < 6; i++) {
            accountNumber.append(random.nextInt(10));
        }

        return accountNumber.toString();
    }
}
