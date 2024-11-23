package org.jala.university.application.service.service_account;

import org.jala.university.application.dto.dto_account.AccountDto;
import org.jala.university.domain.entity.accountEntity.Account;
import org.jala.university.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
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
        account.setCustomerId(customerId);

        Account savedAccount = accountRepository.save(account);

        return mapToDto(savedAccount);
    }

    @Override
    public AccountDto getAccount(Integer id) {
        return accountRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Integer id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void removeAccount(AccountDto accountDto) {
        accountRepository.deleteById(accountDto.getId());
    }

    @Override
    public void updateAccount(AccountDto accountDto) {
        accountRepository.findById(accountDto.getId()).ifPresent(existingAccount -> {
            existingAccount.setAccountNumber(accountDto.getAccountNumber());
            existingAccount.setBalance(accountDto.getBalance());
            existingAccount.setStatus(accountDto.getStatus());
            existingAccount.setCurrency(accountDto.getCurrency());
            existingAccount.setUpdatedAt(LocalDate.now());

            accountRepository.save(existingAccount);
        });
    }

    public String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder("0001-");
        for (int i = 0; i < 6; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    private AccountDto mapToDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getStatus(),
                account.getCurrency(),
                account.getCreatedAt(),
                account.getUpdatedAt(),
                account.getPaymentPassword()
        );
    }
}
