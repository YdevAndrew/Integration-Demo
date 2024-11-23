package org.jala.university.domain.repository;

import org.jala.university.domain.entity.externalEntity.ScheduledPaymentEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledPaymentRepository extends JpaRepository<ScheduledPaymentEntity, Integer> {
}
