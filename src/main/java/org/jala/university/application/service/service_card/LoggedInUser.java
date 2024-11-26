package org.jala.university.application.service.service_card;

import org.jala.university.Config.AppConfig;
import org.jala.university.application.dao_card.CustomerInformationDAO;
import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_account.Customer;
import org.jala.university.domain.repository.repository_account.AccountRepository;
import org.jala.university.domain.repository.repository_account.CustomerRepository;
import org.jala.university.infrastructure.PostgreConnection;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.SQLException;
import java.util.Optional;

public class LoggedInUser {

    private static CustomerRepository customerRepository;
    private static AccountRepository accountRepository;


    public LoggedInUser(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public static void main(String[] args) {
        try {

            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);


            LoggedInUser loggedInUser = context.getBean(LoggedInUser.class);


            Integer loggedInUserId = loggedInUser.getLoggedUserId();
            System.out.println("Usuário logado com ID: " + loggedInUserId);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Retrieve the logged-in user's account ID.
     *
     * @return Integer representing the logged-in user's account ID.
     */
    public Integer getLoggedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String cpf = authentication.getName();


            Customer customer = customerRepository.findByCpf(cpf)
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found"));


            Account account = accountRepository.findAccountByCustomerId(customer.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

            return account.getId();
        }
        return null;
    }


    /**
     * Validates if the given user ID exists in the database.
     *
     * @param id User ID as a String.
     * @return boolean indicating whether the user ID is valid.
     * @throws SQLException if a database error occurs.
     */
    public static boolean validId(String id) throws SQLException {
        PostgreConnection.getInstance("jala_bank").getConnection();
        return CustomerInformationDAO.validId(id);
    }


    public static String LoggedUser() {
        LoggedInUser loggedInUser = new LoggedInUser(customerRepository, accountRepository);
        Integer userId = loggedInUser.getLoggedUserId();

        if (userId != null) {
            return userId.toString();
        } else {
            throw new IllegalArgumentException("Usuário logado não encontrado.");
        }
    }
}
