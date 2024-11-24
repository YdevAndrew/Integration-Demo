package org.jala.university.application.service.service_account;

import org.jala.university.domain.entity.entity_account.Customer;
import org.jala.university.domain.repository.repository_account.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Aqui você busca o usuário pelo e-mail ou CPF
        Customer customer = (Customer) customerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado"));

        return User.withUsername(customer.getEmail())
                .password(Arrays.toString(customer.getPassword())) // Lembre-se de garantir que a senha esteja criptografada
                .authorities("USER") // Você pode configurar roles de usuário, se necessário
                .build();
    }
}
