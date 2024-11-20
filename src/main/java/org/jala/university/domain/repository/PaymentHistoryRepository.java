package org.jala.university.domain.repository;

import org.jala.university.domain.entity.PaymentHistoryEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, Integer> {
}
