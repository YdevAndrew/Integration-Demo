package org.jala.university.domain.repository.repository_loan;


import org.jala.university.domain.entity.entity_loan.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface defines a repository for managing
 * {@link FormEntity} objects.
 * It extends the {@link JpaRepository} interface, providing basic
 * CRUD operations and other database interactions for form entities.
 */
@Repository
public interface FormEntityRepository extends JpaRepository<FormEntity, Integer> {

}
