package org.jala.university.Config;


import org.jala.university.application.service.service_account.AccountService;
import org.jala.university.application.service.service_account.AccountServiceImpl;
import org.jala.university.domain.repository.AccountRepository;
import org.jala.university.presentation.Loan.SpringFXMLLoader;
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

    @Bean
    public TaskScheduler taskScheduler() {

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        return scheduler;
    }

    @Bean
    public AccountService accountService(AccountRepository accountRepository) {
        return new AccountServiceImpl(accountRepository);
    }

    @Bean
    public SpringFXMLLoader springFXMLLoader(ApplicationContext context) {
        return new SpringFXMLLoader(context);
    }
}
