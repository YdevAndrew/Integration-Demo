package org.jala.university.config.config_account;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class PersistenceConfig {

    public static EntityManagerFactory getEntityManagerFactory() {
        Map<String, String> properties = new HashMap<>();

        // Load application_properties env vars
        String jdbcUrl = System.getenv().getOrDefault("JDBC_URL", "jdbc:postgresql://localhost:5432/bank_db");
        String dbUser = System.getenv().getOrDefault("DB_USER", "postgres");
        String dbPassword = System.getenv().getOrDefault("DB_PASSWORD", "postgres");

        properties.put("javax.persistence.jdbc.url", jdbcUrl);
        properties.put("javax.persistence.jdbc.user", dbUser);
        properties.put("javax.persistence.jdbc.password", dbPassword);

        return Persistence.createEntityManagerFactory("bankPU", properties);
    }
}