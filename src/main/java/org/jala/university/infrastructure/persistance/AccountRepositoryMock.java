package org.jala.university.infrastructure.persistance;

import org.jala.university.application.dto.dto_account.AccountDto;
import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.repository.AccountRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

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
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public <S extends Account> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<Account> findAll() {
        return List.copyOf(accounts.values()); // Retorna uma cópia da lista de contas
    }

    @Override
    public List<Account> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
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
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Account> entities) {

    }

    @Override
    public void deleteAll() {

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

    @Override
    public List<Account> findAccountsWithBalanceGreaterThan(Double amount) {
        return List.of();
    }

    @Override
    public List<Account> findAccountsByStatus(String status) {
        return List.of();
    }

    @Override
    public void deleteByAccountNumber(String accountNumber) {

    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Account> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Account> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Account getOne(Integer integer) {
        return null;
    }

    @Override
    public Account getById(Integer integer) {
        return null;
    }

    @Override
    public Account getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Account> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Account> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Account> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Account, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Account> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return null;
    }
}
