package org.jala.university.application.service.service_loan;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.jala.university.application.dto.dto_loan.InstallmentEntityDto;
import org.jala.university.application.dto.dto_loan.LoanEntityDto;
import org.jala.university.application.mapper.mapper_loan.FormEntityMapper;
import org.jala.university.application.mapper.mapper_loan.InstallmentEntityMapper;
import org.jala.university.application.mapper.mapper_loan.LoanEntityMapper;
import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_loan.InstallmentEntity;
import org.jala.university.domain.entity.entity_loan.LoanEntity;
import org.jala.university.domain.entity.entity_loan.enums.Status;
import org.jala.university.domain.repository.repository_account.AccountRepository;
import org.jala.university.domain.repository.repository_loan.LoanEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

/**
 * This class implements the {@link LoanEntityService} interface, providing concrete
 * implementations for managing {@link LoanEntity} objects. It handles operations
 * such as creating, retrieving, updating, deleting loan entities, and managing
 * loan installments and related information.
 */
@Service
public class LoanEntityServiceImpl implements LoanEntityService {

    /**
     * The repository for managing {@link LoanEntity} objects.
     */
    @Autowired
    private LoanEntityRepository loanEntityRepository;

    /**
     * The mapper for converting between {@link LoanEntity} and {@link LoanEntityDto} objects.
     */
    private final LoanEntityMapper loanEntityMapper;

    /**
     * The mapper for converting between {@link InstallmentEntity} and {@link InstallmentEntityDto} objects.
     */
    private final InstallmentEntityMapper installmentEntityMapper;

    /**
     * The task scheduler for scheduling tasks related to loans.
     */
    private final TaskScheduler taskScheduler;

    /**
     * The repository for managing {@link Account} objects.
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * The service for handling loan results and related actions.
     */
    @Autowired
    LoanResultsService loanResultsService;

    /**
     * The service for managing {@link org.jala.university.domain.entity.FormEntity} objects.
     */
    @Autowired
    private FormEntityService formEntityService;

    /**
     * The entity manager for handling persistent entities.
     */
    @Autowired
    private EntityManager entityManager;

    /**
     * Constructor for the LoanEntityServiceImpl class.
     *
     * @param loanEntityMapper        The loan entity mapper instance.
     * @param installmentEntityMapper The installment entity mapper instance.
     * @param formEntityMapper        The form entity mapper instance.
     * @param taskScheduler           The task scheduler instance.
     */
    public LoanEntityServiceImpl(LoanEntityMapper loanEntityMapper, InstallmentEntityMapper installmentEntityMapper,
            FormEntityMapper formEntityMapper, TaskScheduler taskScheduler) {
        this.loanEntityMapper = loanEntityMapper;
        this.installmentEntityMapper = installmentEntityMapper;
        this.taskScheduler = taskScheduler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public LoanEntityDto findById(Integer id) {
        LoanEntity entity = loanEntityRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new IllegalArgumentException("Entity with ID " + id + " not found.");
        }
        return loanEntityMapper.mapTo(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public LoanEntity findEntityById(Integer id) {
        LoanEntity entity = loanEntityRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new IllegalArgumentException("FormEntity not found with ID: " + id);
        }
        return entityManager.merge(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<LoanEntityDto> findAll() {
        return loanEntityRepository.findAll().stream()
                .map(loanEntityMapper::mapTo)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public LoanEntityDto save(LoanEntityDto entityDto) {
        LoanEntity entity = loanEntityMapper.mapFrom(entityDto);
        entity.recalculate();
        entity.generateInstallments();
        entity.generateAndSetDate();
        entity.setForm(formEntityService.findEntityById(entity.getForm().getId()));
        entity.setAccount(accountRepository.findById(formEntityService.getloggedUserId()).orElse(null));
        if (entity.getStatus() == null) {
            entity.setStatus(entity.generateStatus());
        }
        LoanEntity savedEntity = loanEntityRepository.save(entity);

        if (savedEntity.getStatus().getCode() == Status.REVIEW.getCode()) {
            scheduleStatusChange(savedEntity);
        }

        return loanEntityMapper.mapTo(savedEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteById(Integer id) {
        LoanEntity entity = loanEntityRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new IllegalArgumentException("Entity with ID " + id + " not found.");
        }

        loanEntityRepository.delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(LoanEntityDto entityDto) {
        LoanEntity entity = loanEntityMapper.mapFrom(entityDto);
        LoanEntity foundEntity = loanEntityRepository.findById(entity.getId()).orElse(null);

        if (foundEntity == null) {
            throw new IllegalArgumentException("Entity " + entityDto + " not found.");
        }
        loanEntityRepository.delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public LoanEntityDto update(LoanEntityDto entityDto) {
        LoanEntity existingEntity = loanEntityRepository.findById(entityDto.getId()).orElse(null);

        if (existingEntity == null) {
            throw new IllegalArgumentException("Entity with ID " + entityDto.getId() + " not found.");
        }

        LoanEntity updatedEntity = loanEntityMapper.mapFrom(entityDto);
        updatedEntity.setId(entityDto.getId());

        LoanEntity savedEntity = loanEntityRepository.save(updatedEntity);
        return loanEntityMapper.mapTo(savedEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<LoanEntityDto> findLoansByAccountId() {
        Integer id = formEntityService.getloggedUserId();
        List<LoanEntity> loans = loanEntityRepository.findByAccountId(id);
        loans.forEach(LoanEntity::updateStatusFinished);
        return loans.stream()
                .map(loanEntityMapper::mapTo)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Account payInstallmentManually(LoanEntityDto dto) {
        LoanEntity entity = findEntityById(dto.getId());
        Account account = loanResultsService.payInstallment(entity);
        if (account != null) {
            entity.markAsPaid();
            loanEntityRepository.save(entity);
        }
        return account;
    }

    /**
     * {@inheritDoc}
     */
    private void scheduleStatusChange(LoanEntity loanEntity) {
        Instant startTime = Instant.now().plus(Duration.ofSeconds(30));
        taskScheduler.schedule(() -> changeStatusRandomly(loanEntity), startTime);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    private void changeStatusRandomly(LoanEntity loanEntity) {
        Status newStatus = loanEntity.generateStatus();
        loanEntity.setStatus(newStatus);
        loanEntityRepository.save(loanEntity);

        if (newStatus == Status.APPROVED) {
            loanResultsService.sendAmountAccount(loanEntity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    void adjustOverdueInstallments() {
        List<LoanEntity> loans = loanEntityRepository.findByStatusCode(1);

        for (LoanEntity loan : loans) {
            for (InstallmentEntity installment : loan.getInstallments()) {

                if (installment.getDueDate().isBefore(LocalDate.now()) && !installment.getPaid()) {

                    long daysOverdue = ChronoUnit.DAYS.between(installment.getDueDate(), LocalDate.now());
                    double originalAmount = loan.getValueOfInstallments();
                    double updatedAmount = originalAmount + (originalAmount * 0.01 * daysOverdue); //1% per day

                    installment.setAmount(updatedAmount);
                }
            }
        }

        loanEntityRepository.saveAll(loans);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long getPaidInstallments(LoanEntityDto dto) {
        LoanEntity entity = findEntityById(dto.getId());
        return entity.getNumberOfPaidInstallments();
    }

    /**
     * {@inheritDoc}
     */
    public Double getOutstandingBalance(Integer loanId) {
        Double outstandingBalance = loanEntityRepository.getOutstandingBalance(loanId);
        return outstandingBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public InstallmentEntityDto getFirstUnpaidInstallment(LoanEntityDto dto) {
        LoanEntity entity = loanEntityRepository.findByIdWithInstallments(dto.getId());
        if (entity == null || entity.getFirstUnpaidInstallment() == null) {
            return null;
        }
        return installmentEntityMapper.mapTo(entity.getFirstUnpaidInstallment());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public LocalDate getFirstUnpaidInstallmentDate(LoanEntityDto dto) {
        LoanEntity entity = loanEntityRepository.findByIdWithInstallments(dto.getId());
        List<InstallmentEntity> installments = entity.getInstallments();

        return installments.stream()
                .filter(installment -> installment.getPaymentDate() == null)
                .map(InstallmentEntity::getDueDate)
                .findFirst()
                .orElse(null);
    }
}
