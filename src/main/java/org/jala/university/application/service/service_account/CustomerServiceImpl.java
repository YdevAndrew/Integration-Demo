package org.jala.university.application.service.service_account;

import lombok.AllArgsConstructor;
import org.jala.university.application.dto.dto_account.CustomerDto;
import org.jala.university.application.mapper.mapper_account.CustomerMapper;
import org.jala.university.domain.entity.entity_account.Customer;
import org.jala.university.domain.repository.repository_account.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;



    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        // CustomerDTO to Customer (Entity)
        Customer customer = new Customer();
        customer.setFullName(customerDto.getFullName());
        customer.setCpf(customerDto.getCpf());
        customer.setEmail(customerDto.getEmail());
        customer.setGender(customerDto.getGender());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setBirthday(customerDto.getBirthday());
        customer.setPassword(customerDto.getPassword());

        // Atribuindo as novas propriedades de endereço
        customer.setStreet(customerDto.getStreet());
        customer.setDistrict(customerDto.getDistrict());
        customer.setState(customerDto.getState());
        customer.setPostalCode(customerDto.getPostalCode());
        customer.setCountry(customerDto.getCountry());

        // Salvando o cliente
        Customer savedCustomer = customerRepository.save(customer);

        // Retornando o CustomerDto com os dados salvos
        return new CustomerDto(
                savedCustomer.getId(),
                savedCustomer.getFullName(),
                savedCustomer.getCpf(),
                savedCustomer.getEmail(),
                savedCustomer.getGender(),
                savedCustomer.getPhoneNumber(),
                savedCustomer.getBirthday(),
                savedCustomer.getCountry(),
                savedCustomer.getDistrict(),
                savedCustomer.getState(),
                savedCustomer.getPostalCode(),
                savedCustomer.getStreet(),
                savedCustomer.getPassword(),
                savedCustomer.getVerificationCode()
        );
    }

    @Override
    public CustomerDto getCustomer(Integer id) {
        // Busca o cliente pelo ID e mapeia para CustomerDto
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));

        return new CustomerDto(
                customer.getId(),
                customer.getFullName(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getGender(),
                customer.getPhoneNumber(),
                customer.getBirthday(),
                customer.getCountry(),
                customer.getDistrict(),
                customer.getState(),
                customer.getPostalCode(),
                customer.getStreet(),
                customer.getPassword(),
                customer.getVerificationCode()
        );
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        // Lógica para obter todos os clientes
        return null;
    }

    @Override
    public void deleteCustomer(Integer id) {
        // Deletando cliente por ID
        customerRepository.deleteById(id);
    }

    @Override
    public void updateCustomerPassword(CustomerDto customerDto) {
        Customer customer = customerRepository.findById(customerDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + customerDto.getId()));

        customer.setPassword(customerDto.getPassword());
        customerRepository.save(customer);
    }
    @Override
    public CustomerDto getCustomerByVerificationCode(String verificationCode) {
        Customer customer = customerRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o código: " + verificationCode));
        return CustomerMapper.toDto(customer);
    }


    @Override
    public CustomerDto create(CustomerDto customerDto) {
        Customer customer = CustomerMapper.toEntity(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.toDto(savedCustomer);
    }

    @Override
    public boolean authenticate(String cpf, String password) {
        Optional<Customer> optionalCustomer = customerRepository.findByCpf(cpf);
        System.out.println(customerRepository.findByCpf(cpf));

        if (optionalCustomer.isEmpty()) {
            return false;
        }

        System.out.println("find");
        Customer customer = optionalCustomer.get();

        return passwordEncoder.matches(password, new String(customer.getPassword()));
    }

    @Override
    public List<CustomerDto> getAllCustomersSortedByName() {
        return customerRepository.findAllSortedByName().stream()
                .map(CustomerMapper::toDto)
                .collect(Collectors.toList());
    }
}
