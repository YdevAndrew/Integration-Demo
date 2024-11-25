package org.jala.university.domain.repository.repository_card;

import org.jala.university.domain.entity.entity_card.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
