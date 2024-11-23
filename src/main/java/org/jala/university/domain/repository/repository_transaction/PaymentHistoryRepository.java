package org.jala.university.domain.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.jala.university.domain.entity.Account;
import org.jala.university.domain.entity.PaymentHistoryEntity;
import org.jala.university.domain.entity.StatusEntity;
import org.jala.university.domain.entity.TransactionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, Integer> {

    @Query("SELECT p FROM PaymentHistoryEntity p WHERE p.account.id = :accountId")
    List<PaymentHistoryEntity> findAllByAccountId(@Param("accountId") Integer accountId);

    @Query("SELECT p FROM PaymentHistoryEntity p WHERE p.accountReceiver = :accountReceiver")
    List<PaymentHistoryEntity> findAllByReceiver(@Param("accountReceiver") String accountReceiver);

    @Query("SELECT p FROM PaymentHistoryEntity p WHERE p.status = :status AND p.transactionDate BETWEEN :startDate AND :endDate")
    List<PaymentHistoryEntity> findAllByStatusAndTransactionDateBetween(@Param("status") StatusEntity status, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT p FROM PaymentHistoryEntity p WHERE p.account.id = :accountId AND p.status = :status")
    List<PaymentHistoryEntity> findAllByAccountIdAndStatus(@Param("accountId") Integer accountId, @Param("status") StatusEntity status);

    @Query("SELECT p FROM PaymentHistoryEntity p WHERE p.account = :account AND p.status = :status")
    List<PaymentHistoryEntity> findAllByAccountAndStatus(@Param("account") Account account, @Param("status") StatusEntity status);

}