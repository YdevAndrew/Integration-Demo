package org.jala.university.infrastructure.persistance;

import org.jala.university.domain.entity.Account;
import org.jala.university.domain.repository.AccountRepository;

import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AccountRepositoryImpl implements AccountRepository {

    @PersistenceContext // O Spring gerencia a injeção do EntityManager
    private EntityManager entityManager;

    @Override
    public Account save(Account account) {
        if (account.getId() == null) {
            entityManager.persist(account); // Salva a nova conta
        } else {
            account = entityManager.merge(account); // Atualiza uma conta existente
        }
        return account;
    }
    @Override
    public Optional<Account> findById(Integer id) {
        Account account = entityManager.find(Account.class, id);
        return Optional.ofNullable(account);
    }

    @Override
    public List<Account> findAll() {
        return entityManager.createQuery("SELECT a FROM Account a", Account.class)
                .getResultList();
    }

    @Override
    public void delete(Account account) {
        if (entityManager.contains(account)) {
            entityManager.remove(account);
        } else {
            Account managedAccount = entityManager.merge(account);
            entityManager.remove(managedAccount);
        }
    }

    @Override
    public void deleteById(Integer id) {
        findById(id).ifPresent(this::delete);
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return entityManager.createQuery("SELECT accountNumber FROM Account  WHERE Account.accountNumber = :accountNumber", Account.class)
                .setParameter("accountNumber", accountNumber)
                .getResultStream()
                .findFirst();
    }
}
