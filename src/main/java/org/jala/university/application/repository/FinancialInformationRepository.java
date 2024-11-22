package org.jala.university.application.repository;

import org.jala.university.application.model.FinancialInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialInformationRepository extends JpaRepository<FinancialInformation, Integer> {
}
