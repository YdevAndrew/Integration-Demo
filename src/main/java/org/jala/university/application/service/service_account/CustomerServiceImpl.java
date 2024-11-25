package org.jala.university.application.service.service_account;

import lombok.AllArgsConstructor;
import org.jala.university.application.dto.dto_account.CustomerDto;
import org.jala.university.application.mapper.mapper_account.CustomerMapper;
import org.jala.university.domain.entity.entity_account.Customer;
import org.jala.university.domain.repository.repository_account.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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

    public CustomerDto getCustomerByCpf(String cpf) {
        try {
            // Validação do parâmetro de entrada
            if (cpf == null || cpf.trim().isEmpty()) {
                throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
            }

            // Busca o cliente
            Customer customer = customerRepository.findByCpf(cpf)
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o CPF: " + cpf));

            // Validação adicional do objeto customer
            if (customer == null) {
                throw new EntityNotFoundException("Cliente recuperado é nulo para o CPF: " + cpf);
            }

            // Construção do DTO com validações null-safe
            return new CustomerDto(
                    customer.getId(),
                    customer.getFullName() != null ? customer.getFullName() : "",
                    customer.getCpf(),
                    customer.getEmail() != null ? customer.getEmail() : "",
                    customer.getGender() != null ? customer.getGender() : "",
                    customer.getPhoneNumber() != null ? customer.getPhoneNumber() : "",
                    customer.getBirthday(),
                    customer.getCountry() != null ? customer.getCountry() : "",
                    customer.getDistrict() != null ? customer.getDistrict() : "",
                    customer.getState() != null ? customer.getState() : "",
                    customer.getPostalCode() != null ? customer.getPostalCode() : "",
                    customer.getStreet() != null ? customer.getStreet() : "",
                    customer.getPassword() != null ? customer.getPassword() : "".getBytes(),
                    customer.getVerificationCode() != null ? customer.getVerificationCode() : ""
            );
        } catch (EntityNotFoundException e) {
            throw e;  // Relanças a exceção específica
        } catch (Exception e) {
            // Log do erro
            throw new RuntimeException("Erro ao buscar cliente por CPF: " + cpf, e);
        }
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

        if (optionalCustomer.isEmpty()) {
            return false;
        }

        Customer customer = optionalCustomer.get();

        // Converte a senha de bytea (byte[]) para String (caso necessário)
        String storedPassword = new String(customer.getPassword(), StandardCharsets.UTF_8);

        // Verifica se a senha fornecida corresponde à armazenada no banco de dados
        if (!passwordEncoder.matches(password, storedPassword)) {
            return false; // Senha inválida
        }

        // Se a senha for válida, retorna true
        return true; // Autenticação bem-sucedida
    }

    @Override
    public List<CustomerDto> getAllCustomersSortedByName() {
        return customerRepository.findAllSortedByName().stream()
                .map(CustomerMapper::toDto)
                .collect(Collectors.toList());


    }
    @Override
    public CustomerDto getCustomerByEmail(String email) {
        // Busca o cliente pelo email
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o email: " + email));

        // Converte o Customer para CustomerDto e retorna
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
}
