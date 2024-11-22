package org.jala.university.application.repository;


import org.jala.university.application.model.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, Integer> {

}