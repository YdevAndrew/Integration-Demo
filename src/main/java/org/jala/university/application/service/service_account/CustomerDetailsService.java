package org.jala.university.application.service.service_account;

import org.jala.university.domain.entity.entity_account.Customer;
import org.jala.university.domain.repository.repository_account.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        // Busca o usuário pelo CPF
        Customer customer = customerRepository.findByCpf(cpf)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        // Converte a senha de bytea (byte[]) para String (presumindo que foi criptografada corretamente)
        String encodedPassword = new String(customer.getPassword(), StandardCharsets.UTF_8);

        // Retorna o UserDetails com a senha criptografada
        return User.withUsername(customer.getCpf())  // Usando CPF como nome de usuário
                .password(encodedPassword)  // Senha já criptografada
                .authorities("USER")  // Você pode configurar roles de usuário, se necessário
                .build();
    }
}



