package org.jala.university.domain.repository;

import org.jala.university.domain.entity.entity_external.ScheduledPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledPaymentRepository extends JpaRepository<ScheduledPaymentEntity, Integer> {
}
