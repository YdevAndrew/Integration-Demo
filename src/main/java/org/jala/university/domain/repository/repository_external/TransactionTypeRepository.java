package org.jala.university.domain.repository.repository_external;

import org.jala.university.domain.entity.entity_external.TransactionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionTypeEntity, Integer> {
}

