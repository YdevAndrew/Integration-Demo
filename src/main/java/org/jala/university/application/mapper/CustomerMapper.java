package org.jala.university.application.mapper;

import org.jala.university.domain.entity.Customer;
import org.jala.university.application.dto.CustomerDto;

public class CustomerMapper {

    public static CustomerDto toDto(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setFullName(customer.getFullName());
        dto.setCpf(customer.getCpf());
        dto.setEmail(customer.getEmail());
        dto.setGender(customer.getGender());
        dto.setBirthday(customer.getBirthday());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setStreet(customer.getStreet());
        dto.setCountry(customer.getCountry());
        dto.setDistrict(customer.getDistrict());
        dto.setState(customer.getState());
        dto.setPostalCode(customer.getPostalCode());
        dto.setPassword(customer.getPassword());
        dto.setVerificationCode(customer.getVerificationCode());

        return dto;
    }

    public static Customer toEntity(CustomerDto dto) {
        if (dto == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setFullName(dto.getFullName());
        customer.setCpf(dto.getCpf());
        customer.setEmail(dto.getEmail());
        customer.setGender(dto.getGender());
        customer.setBirthday(dto.getBirthday());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setStreet(dto.getStreet());
        customer.setCountry(dto.getCountry());
        customer.setDistrict(dto.getDistrict());
        customer.setState(dto.getState());
        customer.setPostalCode(dto.getPostalCode());
        customer.setPassword(dto.getPassword());
        customer.setVerificationCode(dto.getVerificationCode());

        return customer;
    }
}
