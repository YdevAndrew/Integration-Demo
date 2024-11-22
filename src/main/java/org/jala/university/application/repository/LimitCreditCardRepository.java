package org.jala.university.application.repository;

import org.jala.university.services.endpoint.modal.LimitCreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitCreditCardRepository extends JpaRepository<LimitCreditCard, Integer> {
}
