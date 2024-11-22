package org.jala.university.services.endpoint.repository;

import org.jala.university.services.endpoint.modal.PurchaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, Integer> {
}
