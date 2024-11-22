package org.jala.university.services.endpoint.repository;

import org.jala.university.services.endpoint.modal.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
