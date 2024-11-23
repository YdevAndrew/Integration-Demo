package org.jala.university.application.service.service_account;

import org.jala.university.application.dto.dto_account.CustomerDto;

import java.util.List;

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
