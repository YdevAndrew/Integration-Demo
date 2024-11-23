package org.jala.university.domain.repository;

import org.jala.university.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // Busca cliente pelo CPF
    @Query("SELECT c FROM Customer c WHERE c.cpf = :cpf")
    Optional<Customer> findByCpf(@Param("cpf") String cpf);

    // Busca cliente pelo código de verificação
    @Query("SELECT c FROM Customer c WHERE c.verificationCode = :verificationCode")
    Optional<Customer> findByVerificationCode(@Param("verificationCode") String verificationCode);

    // Busca clientes por país
    @Query("SELECT c FROM Customer c WHERE c.country = :country")
    List<Customer> findByCountry(@Param("country") String country);

    // Busca clientes por estado
    @Query("SELECT c FROM Customer c WHERE c.state = :state")
    List<Customer> findByState(@Param("state") String state);

    // Busca todos os clientes ordenados por nome
    @Query("SELECT c FROM Customer c ORDER BY c.fullName ASC")
    List<Customer> findAllSortedByName();



    // Busca clientes ativos (exemplo, supomos que há um campo booleano 'active' na entidade Customer)
   // @Query("SELECT c FROM Customer c WHERE c.active = true")
 //   List<Customer> findActiveCustomers();
}
