package org.jala.university.infrastructure.persistance;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.jala.university.domain.entity.Customer;
import org.jala.university.domain.repository.CustomerRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CustomerRepositoryImpl implements CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            entityManager.persist(customer); // Se o ID for nulo, é um novo cliente
        } else {
            entityManager.merge(customer); // Se já tiver ID, é uma atualização
        }
        return customer;
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Customer.class, id)); // Buscar cliente por ID
    }

    @Override
    public List<Customer> findAll() {
        return entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList(); // Retorna todos os clientes
    }

    @Override
    public void deleteById(Integer id) {
        findById(id).ifPresent(customer -> entityManager.remove(customer)); // Deletar cliente por ID, se existir
    }

    @Override
    public Optional<Customer> findByVerificationCode(String verificationCode) {
        try {
            TypedQuery<Customer> query = entityManager.createQuery(
                    "SELECT c FROM Customer c WHERE c.verificationCode = :code",
                    Customer.class
            );
            query.setParameter("code", verificationCode);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByCpf(String cpf) {
        try {
            TypedQuery<Customer> query = entityManager.createQuery(
                    "SELECT c FROM Customer c WHERE c.cpf = :cpf",
                    Customer.class
            );
            query.setParameter("cpf", cpf);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
