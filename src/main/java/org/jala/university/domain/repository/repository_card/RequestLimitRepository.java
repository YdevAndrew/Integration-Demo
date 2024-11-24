package org.jala.university.domain.repository.repository_card;

import org.jala.university.domain.entity.entity_card.RequestLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLimitRepository  extends JpaRepository<RequestLimit, Integer>  {
}
