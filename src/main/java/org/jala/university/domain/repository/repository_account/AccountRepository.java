package org.jala.university.domain.repository.repository_account;

import org.jala.university.domain.entity.entity_account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {


    @Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
    Optional<Account> findByAccountNumber(@Param("accountNumber") String accountNumber);


    @Query("SELECT a FROM Account a WHERE a.balance > :amount")
    List<Account> findAccountsWithBalanceGreaterThan(@Param("amount") Double amount);


    @Query("SELECT a FROM Account a WHERE a.status = :status")
    List<Account> findAccountsByStatus(@Param("status") String status);


    @Query("DELETE FROM Account a WHERE a.accountNumber = :accountNumber")
    void deleteByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("SELECT a.balance FROM Account a WHERE a.customerId = :customerId")
    BigDecimal findByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT a FROM Account a WHERE a.customerId = :customerId")
    Optional<Account> findAccountByCustomerId(@Param("customerId") Integer customerId);

}
