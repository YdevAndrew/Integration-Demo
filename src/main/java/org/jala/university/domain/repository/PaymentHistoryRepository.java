package org.jala.university.domain.repository;

import org.jala.university.domain.entity.entity_external.PaymentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, Integer> {
}
