package org.jala.university.infrastructure.persistance;

import org.jala.university.application.dto.AccountDto;
import org.jala.university.domain.entity.Account;
import org.jala.university.domain.repository.AccountRepository;
 import org.jala.university.infrastructure.persistance.AccountGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountRepositoryMock implements AccountRepository {

    private final Map<Integer, Account> accounts = new HashMap<>();
    private Integer idCounter = 1; // Contador para gerar IDs únicos

    public AccountRepositoryMock() {
        // Popula dados de exemplo
        accounts.putAll(AccountGenerator.generateRandomAccounts(10)); // Gera 10 contas aleatórias
    }

    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public Optional<Account> findById(Integer id) {
        return Optional.ofNullable(accounts.get(id)); // Retorna um Optional vazio se não encontrar
    }

    @Override
    public List<Account> findAll() {
        return List.copyOf(accounts.values()); // Retorna uma cópia da lista de contas
    }


    public Account save(AccountDto accountDto) {
        Account account = new Account(); // Cria uma nova instância de Account
        account.setId(accountDto.getId() != null ? accountDto.getId() : generateId());
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setBalance(accountDto.getBalance());
        account.setStatus(accountDto.getStatus());
        account.setCurrency(accountDto.getCurrency());

        accounts.put(account.getId(), account); // Armazena a conta no mapa
        return account; // Retorna a conta salva
    }

    @Override
    public void delete(Account account) {
        accounts.remove(account.getId()); // Remove a conta pelo ID
    }

    @Override
    public void deleteById(Integer id) {
        accounts.remove(id); // Remove a conta pelo ID
    }

    private Integer generateId() {
        return idCounter++; // Gera um ID único e incrementa o contador
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accounts.values().stream()
                .filter(account -> accountNumber.equals(account.getAccountNumber()))
                .findFirst();
    }
}
