package org.jala.university.infrastructure.config;

import org.jala.university.application.service.CustomerService;
import org.jala.university.application.service.CustomerServiceImpl;
import org.jala.university.domain.repository.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages = "org.jala.university")
public class SpringConfig {

}
