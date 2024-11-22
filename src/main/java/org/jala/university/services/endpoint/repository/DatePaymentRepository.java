package org.jala.university.services.endpoint.repository;

import org.jala.university.services.endpoint.modal.DatePayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatePaymentRepository extends JpaRepository<DatePayment, Integer> {
}
