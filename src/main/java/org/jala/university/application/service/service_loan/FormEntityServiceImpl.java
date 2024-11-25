package org.jala.university.application.service.service_loan;

import java.util.List;
import java.util.stream.Collectors;

import org.jala.university.application.dto.dto_loan.FormEntityDto;
import org.jala.university.application.mapper.mapper_loan.FormEntityMapper;
import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_account.Customer;
import org.jala.university.domain.entity.entity_loan.FormEntity;
import org.jala.university.domain.repository.repository_account.AccountRepository;
import org.jala.university.domain.repository.repository_account.CustomerRepository;
import org.jala.university.domain.repository.repository_loan.FormEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class FormEntityServiceImpl implements FormEntityService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FormEntityRepository formEntityRepository;
    private final FormEntityMapper formEntityMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public FormEntityServiceImpl(FormEntityMapper formEntityMapper) {
        this.formEntityMapper = formEntityMapper;
    }

    @Override
    public  Integer getloggedUserId() {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {

            Customer customer = customerRepository.findByCpf(authentication.getName())
                    .orElseThrow(() -> new IllegalArgumentException("customer not found"));

            Account account = accountRepository.findAccountByCustomerId(customer.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));
            return account.getId();
        }
        return null;
    }
    private String userAccountNumber;


    @Override
    @Transactional(readOnly = true)
    public FormEntityDto findById(Integer id) {
        FormEntity entity = formEntityRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new IllegalArgumentException("FormEntity not found with ID: " + id);
        }

        return formEntityMapper.mapTo(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public FormEntity findEntityById(Integer id) {
        FormEntity entity = formEntityRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new IllegalArgumentException("FormEntity not found with ID: " + id);
        }
        return entityManager.merge(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormEntityDto> findAll() {
        return formEntityRepository.findAll().stream()
                .map(formEntityMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FormEntityDto save(FormEntityDto formEntityDto) {
        FormEntity formEntity = formEntityMapper.mapFrom(formEntityDto);
        formEntity.calculateMaximumAmount();

        FormEntity savedEntity = formEntityRepository.save(formEntity);

        return formEntityMapper.mapTo(savedEntity);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        FormEntity entity = formEntityRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new IllegalArgumentException("FormEntity not found with ID: " + id);
        }

        formEntityRepository.delete(entity);
    }

    @Override
    @Transactional
    public void delete(FormEntityDto formEntityDto) {
        if (formEntityDto == null || formEntityDto.getId() == null) {
            throw new IllegalArgumentException("Invalid FormEntityDto: ID must not be null.");
        }

        FormEntity entity = formEntityRepository.findById(formEntityDto.getId()).orElse(null);
        if (entity == null) {
            throw new IllegalArgumentException("FormEntity not found with ID: " + formEntityDto.getId());
        }

        formEntityRepository.delete(entity);
    }

    @Override
    @Transactional
    public FormEntityDto update(Integer id, FormEntityDto formEntityDto) {
        FormEntity existingEntity = formEntityRepository.findById(id).orElse(null);

        if (existingEntity == null) {
            throw new IllegalArgumentException("FormEntity not found with ID: " + id);
        }

        FormEntity updatedEntity = formEntityMapper.mapFrom(formEntityDto);
        updatedEntity.setId(id);

        FormEntity savedEntity = formEntityRepository.save(updatedEntity);
        return formEntityMapper.mapTo(savedEntity);
    }
}
