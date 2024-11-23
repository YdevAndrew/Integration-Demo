package org.jala.university.Config;


import org.jala.university.application.mapper.mapper_loan.FormEntityMapper;
import org.jala.university.application.mapper.mapper_loan.InstallmentEntityMapper;
import org.jala.university.application.mapper.mapper_loan.LoanEntityMapper;
import org.jala.university.application.service.service_account.AccountService;
import org.jala.university.application.service.service_account.AccountServiceImpl;
import org.jala.university.application.service.service_loan.FormEntityService;
import org.jala.university.application.service.service_loan.FormEntityServiceImpl;
import org.jala.university.application.service.service_loan.LoanEntityService;
import org.jala.university.application.service.service_loan.LoanEntityServiceImpl;
import org.jala.university.application.service.service_loan.LoanResultsService;
import org.jala.university.application.service.service_loan.LoanResultsServiceImpl;
import org.jala.university.domain.repository.repository_account.AccountRepository;
import org.jala.university.presentation.controller.Loan.SpringFXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ComponentScan(basePackages = "org.jala.university")
@EnableJpaRepositories(basePackages = "org.jala.university")
public class AppConfig {

    ///////////////////////////General/////////////////////////////////////

    @Bean
    public TaskScheduler taskScheduler() {

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        return scheduler;
    }

    @Bean
    public SpringFXMLLoader springFXMLLoader(ApplicationContext context) {
        return new SpringFXMLLoader(context);
    }

    ///////////////////////////Account/////////////////////////////////////

    @Bean
    public AccountService accountService(AccountRepository accountRepository) {
        return new AccountServiceImpl(accountRepository);
    }

    ///////////////////////////Loan/////////////////////////////////////
    
    @Bean
    public FormEntityMapper formEntityMapper() {
        return new FormEntityMapper();
    }

    @Bean
    public InstallmentEntityMapper installmentEntityMapper() {
        return new InstallmentEntityMapper();
    }

    @Bean
    public LoanEntityMapper loanEntityMapper() {
        return new LoanEntityMapper();
    }

    @Bean
    public FormEntityService formEntityService(FormEntityMapper formEntityMapper) {
        return new FormEntityServiceImpl(formEntityMapper);
    }

    @Bean
    public LoanEntityService loanEntityService(LoanEntityMapper loanEntityMapper, InstallmentEntityMapper installmentEntityMapper, FormEntityMapper formEntityMapper,
            TaskScheduler taskScheduler) {
        return new LoanEntityServiceImpl(loanEntityMapper,installmentEntityMapper, formEntityMapper, taskScheduler);
    }

    @Bean
    public LoanResultsService loanResultsService() {
        return new LoanResultsServiceImpl();
    }
}
