package org.jala.university.insfrastructe.persistance;

import org.jala.university.domain.entity.entity_account.Customer;
import org.jala.university.domain.repository.repository_account.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerRepositoryImplTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerRepositoryImplTest customerRepositoryImplTest;

    @Test
    public void testSaveAndFindById() {
        // Criando um novo cliente
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFullName("Gwyn");  // Renomeado para 'full_name'
        customer.setCpf("123.123.123-02");
        customer.setEmail("gwn@gmail.com");
        customer.setGender("Masculino");
        customer.setPhoneNumber("(11) 99999-9929");
        customer.setBirthday(LocalDate.of(1990, 7, 12));
        customer.setProfilePicture(null);  // Opcional, pode ser preenchido com o URL da imagem, se necessário

        // Definindo o endereço em várias colunas
        customer.setStreet("Avenida 3");
        customer.setDistrict("Firelink Shrine");
        customer.setState("God");
        customer.setPostalCode("93700-000");
        customer.setCountry("Age of Fire");

        // Simulando o comportamento do save
        when(repository.save(customer)).thenReturn(customer);

        // Simulando o comportamento do findById
        when(repository.findById(1)).thenReturn(Optional.of(customer));

        // Salvando o cliente no repositório (mocked)
        Customer savedCustomer = repository.save(customer);

        // Buscando o cliente pelo id (mocked)
        Optional<Customer> foundCustomer = repository.findById(savedCustomer.getId());

        // Validando que o cliente foi encontrado e os dados estão corretos
        assertTrue(foundCustomer.isPresent());
        assertEquals(savedCustomer.getId(), foundCustomer.get().getId());
        assertEquals("Gwyn", foundCustomer.get().getFullName());  // Renomeado para 'full_name'
        assertEquals("123.123.123-02", foundCustomer.get().getCpf());
        assertEquals("gwn@gmail.com", foundCustomer.get().getEmail());
        assertEquals("Masculino", foundCustomer.get().getGender());
        assertEquals("(11) 99999-9929", foundCustomer.get().getPhoneNumber());
        assertEquals(LocalDate.of(1990, 7, 12).toString(), foundCustomer.get().getBirthday().toString());

        // Validando os campos de endereço
        assertEquals("Avenida 3", foundCustomer.get().getStreet());
        assertEquals("Firelink Shrine", foundCustomer.get().getDistrict());
        assertEquals("God", foundCustomer.get().getState());
        assertEquals("93700-000", foundCustomer.get().getPostalCode());
        assertEquals("Age of Fire", foundCustomer.get().getCountry());
    }
}
