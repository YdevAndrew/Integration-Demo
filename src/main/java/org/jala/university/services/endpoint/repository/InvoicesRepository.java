package org.jala.university.services.endpoint.repository;

import org.jala.university.services.endpoint.modal.Invoices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoicesRepository extends JpaRepository<Invoices, Integer> {
}
