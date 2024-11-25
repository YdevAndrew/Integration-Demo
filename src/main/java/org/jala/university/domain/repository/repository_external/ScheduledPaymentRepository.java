package org.jala.university.domain.repository.repository_external;

import org.jala.university.domain.entity.entity_external.ScheduledPaymentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ScheduledPaymentRepository extends JpaRepository<ScheduledPaymentEntity, Integer> {
}
