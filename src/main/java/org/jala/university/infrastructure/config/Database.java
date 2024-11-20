package org.jala.university.infrastructure.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Database {

    private static EntityManager entityManager = null;
    private static EntityManagerFactory entityManagerFactory = null;

    public static synchronized EntityManager getConnection() {
        if (entityManager == null) {
            try {
                entityManagerFactory = Persistence.createEntityManagerFactory("jala_bank");
                entityManager = entityManagerFactory.createEntityManager();

            } catch (Exception e) {
                throw new ExceptionInInitializerError("Error when connecting: " + e.getMessage());
            }
        }
        return entityManager;
    }

    public static void closeConnection() {
        if (entityManager != null && entityManager.isOpen()) {
            try {
                entityManager.close();
                entityManagerFactory.close();
            } catch (Exception e) {
                throw new ExceptionInInitializerError("Error when closing the connection: " + e.getMessage());
            }
        }
    }
}