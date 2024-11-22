package org.jala.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 * to run use mvn spring-boot:run
 * responsible to run the springboot application with the controllers
 *
 */
@SpringBootApplication(scanBasePackages = "org.jala.university.services.endpoint.controller")
public class SpringBootApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }
}
