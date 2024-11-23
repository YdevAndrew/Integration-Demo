package org.jala.university.domain.repository;

import org.jala.university.domain.entity.TransactionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionTypeEntity, Integer> {

    // Query personalizada para encontrar um TransactionTypeEntity pelo nome do tipo de transação
    @Query("SELECT t FROM TransactionTypeEntity t WHERE t.transactionTypeName = :transactionTypeName")
    Optional<TransactionTypeEntity> findByTransactionTypeName(@Param("transactionTypeName") String transactionTypeName);
}
