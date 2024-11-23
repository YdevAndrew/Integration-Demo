package org.jala.university.application.service;

import org.jala.university.application.dto.CustomerDto;
import org.jala.university.domain.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);
    CustomerDto getCustomer(Integer id);
    List<CustomerDto> getAllCustomers();
    void deleteCustomer(Integer id);
    void updateCustomerPassword(CustomerDto customerDto);

    CustomerDto getCustomerByVerificationCode(String verificationCode);

    CustomerDto create(CustomerDto customerDto);

    boolean authenticate(String cpf, String password);

    List<CustomerDto> getAllCustomersSortedByName();
}
