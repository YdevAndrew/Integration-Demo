package org.jala.university.domain.repository;

import org.jala.university.domain.entity.TransactionTypeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionTypeRepository extends JpaRepository<TransactionTypeEntity, Integer> {
}

