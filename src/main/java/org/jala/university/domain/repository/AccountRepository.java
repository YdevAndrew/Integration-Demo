package org.jala.university.domain.repository;

import org.jala.university.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Account save(Account account); // Mantém Account como tipo para salvar

    Optional<Account> findById(Integer id);

    List<Account> findAll();

    void delete(Account account);

    void deleteById(Integer id);
    Optional<Account> findByAccountNumber(String accountNumber);
}



