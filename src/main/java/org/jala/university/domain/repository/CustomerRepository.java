package org.jala.university.domain.repository;

import org.jala.university.domain.entity.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(Integer id);
    List<Customer> findAll();
    void deleteById(Integer id);
    Optional<Customer> findByVerificationCode(String verificationCode);
    Optional<Customer> findByCpf(String cpf);
}
