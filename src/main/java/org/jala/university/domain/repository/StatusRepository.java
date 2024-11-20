package org.jala.university.domain.repository;

import org.jala.university.domain.entity.StatusEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
}

