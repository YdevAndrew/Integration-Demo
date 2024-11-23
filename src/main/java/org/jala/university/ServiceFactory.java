package org.jala.university;

import org.jala.university.application.service.service_account.AccountService;
import org.jala.university.application.service.service_account.CustomerService;
import org.jala.university.application.service.service_account.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component // Tornando o ServiceFactory um Bean do Spring
public class ServiceFactory {

    private static CustomerService customerService;
    private static AuthenticationService authenticationService;
    private static AccountService accountService;

    @Qualifier("accountServiceImpl")
    @Autowired
    private AccountService accountServiceBean;

    @Autowired
    private static CustomerService customerServiceBean;

    @Autowired
    private static AuthenticationService authenticationServiceBean;

    public static CustomerService getCustomerService() {
        if (customerService == null) {
            customerService = customerServiceBean; // Usando o bean do Spring
        }
        return customerService;
    }

    public static AuthenticationService getAuthenticationService() {
        if (authenticationService == null) {
            authenticationService = authenticationServiceBean; // Usando o bean do Spring
        }
        return authenticationService;
    }

    public static AccountService accountService() {
        return null;
    }

    public AccountService getAccountService() {
        if (accountService == null) {
            accountService = accountServiceBean; // Usando o bean do Spring
        }
        return accountService;
    }
}
