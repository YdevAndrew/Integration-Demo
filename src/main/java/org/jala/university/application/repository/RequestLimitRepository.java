package org.jala.university.application.repository;

import org.jala.university.services.endpoint.modal.RequestLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLimitRepository  extends JpaRepository<RequestLimit, Integer>  {
}
