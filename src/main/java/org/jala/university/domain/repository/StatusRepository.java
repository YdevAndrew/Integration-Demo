package org.jala.university.domain.repository;

import org.jala.university.domain.entity.externalEntity.StatusEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
}

