package org.jala.university.domain.repository.repository_loan;


import org.jala.university.domain.entity.entity_loan.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormEntityRepository extends JpaRepository<FormEntity, Integer> {

}