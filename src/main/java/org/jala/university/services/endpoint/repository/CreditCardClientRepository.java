package org.jala.university.services.endpoint.repository;

import org.jala.university.services.endpoint.modal.CreditCardClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardClientRepository extends JpaRepository<CreditCardClient, Integer> {
}
