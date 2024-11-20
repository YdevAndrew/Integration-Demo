package org.jala.university.domain.repository;

import org.jala.university.domain.entity.ScheduledPaymentEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduledPaymentRepository extends JpaRepository<ScheduledPaymentEntity, Integer> {
}
