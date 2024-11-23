package org.jala.university.application.service;

import org.jala.university.application.dto.dto_account.CustomerDto;
import org.jala.university.application.service.service_account.CustomerService;
import org.jala.university.application.service.service_account.CustomerServiceImpl;
import org.jala.university.domain.entity.entity_account.Customer;
import org.jala.university.domain.repository.repository_account.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        customerService = new CustomerServiceImpl(customerRepository, passwordEncoder);

        // Configurar o comportamento do passwordEncoder mock para qualquer string
        when(passwordEncoder.encode(Mockito.anyString())).thenReturn("hashedPassword123");
    }

    @Test
    public void testCreateCustomer() {
        // Arrange
        String rawPassword = "senha123";  // Senha original
        CustomerDto customerDto = CustomerDto.builder()
                .fullName("Rafael")
                .cpf("123.123.123-00")
                .email("example@domain.com")
                .gender("Masculino")
                .phoneNumber("(12) 93456-7890")
                .birthday(LocalDate.of(2004, 3, 22))
                .street("Avenida Dark Souls 3")
                .country("Age of Fire")
                .district("Firelink Shrine")
                .state("Hollow")
                .postalCode("93700-000")
                .verificationCode("263522")
                .password(rawPassword.getBytes())  // Usar a senha original aqui
                .build();

        // Mocking the repository save method
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer customer = invocation.getArgument(0);

            Customer savedCustomer = new Customer();
            savedCustomer.setId(1);
            savedCustomer.setFullName(customer.getFullName());
            savedCustomer.setCpf(customer.getCpf());
            savedCustomer.setEmail(customer.getEmail());
            savedCustomer.setGender(customer.getGender());
            savedCustomer.setPhoneNumber(customer.getPhoneNumber());
            savedCustomer.setBirthday(customer.getBirthday());
            savedCustomer.setStreet(customer.getStreet());
            savedCustomer.setCountry(customer.getCountry());
            savedCustomer.setDistrict(customer.getDistrict());
            savedCustomer.setState(customer.getState());
            savedCustomer.setPostalCode(customer.getPostalCode());
            savedCustomer.setPassword(customer.getPassword()); // Adicionar a senha hasheada

            return savedCustomer;
        });

        // Act
        CustomerDto savedCustomer = customerService.createCustomer(customerDto);

        // Assert
        assertEquals(customerDto.getCpf(), savedCustomer.getCpf());
        assertEquals(customerDto.getEmail(), savedCustomer.getEmail());
        assertEquals(customerDto.getFullName(), savedCustomer.getFullName());
        assertEquals(customerDto.getPhoneNumber(), savedCustomer.getPhoneNumber());
        assertEquals(customerDto.getGender(), savedCustomer.getGender());
        assertEquals(customerDto.getBirthday(), savedCustomer.getBirthday());
        assertEquals(customerDto.getStreet(), savedCustomer.getStreet());
        assertEquals(customerDto.getCountry(), savedCustomer.getCountry());
        assertEquals(customerDto.getDistrict(), savedCustomer.getDistrict());
        assertEquals(customerDto.getState(), savedCustomer.getState());
        assertEquals(customerDto.getPostalCode(), savedCustomer.getPostalCode());
    }}

