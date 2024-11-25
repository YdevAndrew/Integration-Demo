package org.jala.university.config.config_account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseConfig {
    private static EntityManagerFactory entityManagerFactory;
    private static ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();

    public static void initialize() {
        if (entityManagerFactory == null) {
            try {
                entityManagerFactory = Persistence.createEntityManagerFactory("bankPU");
                System.out.println("EntityManagerFactory initialized.");
            } catch (Exception e) {
                System.err.println("Error initializing EntityManagerFactory: " + e.getMessage());
                throw new RuntimeException("Failed to initialize database connection", e);
            }
        }
    }

    public static EntityManager getEntityManager() {
        EntityManager entityManager = entityManagerThreadLocal.get();
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = entityManagerFactory.createEntityManager();
            entityManagerThreadLocal.set(entityManager);
        }
        return entityManager;
    }

    public static void closeEntityManager() {
        EntityManager entityManager = entityManagerThreadLocal.get();
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            entityManagerThreadLocal.remove();
            System.out.println("EntityManager closed.");
        }
    }

    public static void shutdown() {
        closeEntityManager();  // Fechar o EntityManager atual se estiver aberto.
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
            entityManagerFactory = null; // Opcional: permitir reinicialização
            System.out.println("EntityManagerFactory closed.");
        }
    }
}
