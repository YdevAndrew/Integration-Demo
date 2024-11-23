package org.jala.university.domain.repository;

import org.jala.university.domain.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {

    // Usando uma query JPQL personalizada para buscar pelo statusName
    @Query("SELECT s FROM StatusEntity s WHERE s.statusName = :statusName")
    Optional<StatusEntity> findByStatusName(@Param("statusName") String statusName);
}
