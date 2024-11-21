package org.jala.university.infrastructure.persistance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.jala.university.domain.entity.PasswordRecovery;
import org.jala.university.domain.repository.PasswordRecoveryRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Repository
public class PasswordRecoveryRepositoryImpl implements PasswordRecoveryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PasswordRecovery save(PasswordRecovery passwordRecovery) {
        if (passwordRecovery.getId() == null) {
            entityManager.persist(passwordRecovery);
            return passwordRecovery;
        }
        return entityManager.merge(passwordRecovery);
    }

    @Override
    public Optional<PasswordRecovery> findByToken(String token) {
        TypedQuery<PasswordRecovery> query = entityManager.createQuery(
                "SELECT pr FROM PasswordRecovery pr WHERE pr.recoveryToken = :token",
                PasswordRecovery.class
        );
        query.setParameter("token", token);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PasswordRecovery> findByCustomerId(Integer customerId) {
        TypedQuery<PasswordRecovery> query = entityManager.createQuery(
                "SELECT pr FROM PasswordRecovery pr WHERE pr.customer.id = :customerId",
                PasswordRecovery.class
        );
        query.setParameter("customerId", customerId);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(PasswordRecovery passwordRecovery) {
        if (entityManager.contains(passwordRecovery)) {
            entityManager.remove(passwordRecovery);
        } else {
            entityManager.remove(entityManager.merge(passwordRecovery));
        }
    }

    @Override
    public void deleteExpiredTokens() {
        entityManager.createQuery(
                        "DELETE FROM PasswordRecovery pr WHERE pr.expiresAt < :currentTime"
                )
                .setParameter("currentTime", Timestamp.from(Instant.now()))
                .executeUpdate();
    }
}
