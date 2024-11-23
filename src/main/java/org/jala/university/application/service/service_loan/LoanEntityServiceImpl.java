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

@Service
public class LoanEntityServiceImpl implements LoanEntityService {

    @Autowired
    private LoanEntityRepository loanEntityRepository;
    private final LoanEntityMapper loanEntityMapper;
    private final InstallmentEntityMapper installmentEntityMapper;
    private final TaskScheduler taskScheduler;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    LoanResultsService loanResultsService;

    @Autowired
    private FormEntityService formEntityService;

    @Autowired
    private EntityManager entityManager;

    public LoanEntityServiceImpl(LoanEntityMapper loanEntityMapper, InstallmentEntityMapper installmentEntityMapper, FormEntityMapper formEntityMapper, TaskScheduler taskScheduler) {
        this.loanEntityMapper = loanEntityMapper;
        this.installmentEntityMapper = installmentEntityMapper;
        this.taskScheduler = taskScheduler;
    }

    @Override
    @Transactional(readOnly = true)
    public LoanEntityDto findById(Integer id) {
        LoanEntity entity = loanEntityRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new IllegalArgumentException("Entity with ID " + id + " not found.");
        }
        return loanEntityMapper.mapTo(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public LoanEntity findEntityById(Integer id) {
        LoanEntity entity = loanEntityRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new IllegalArgumentException("FormEntity not found with ID: " + id);
        }
        return entityManager.merge(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanEntityDto> findAll() {
        return loanEntityRepository.findAll().stream()
                .map(loanEntityMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LoanEntityDto save(LoanEntityDto entityDto) {
        LoanEntity entity = loanEntityMapper.mapFrom(entityDto);
        entity.recalculate();
        entity.generateInstallments();
        entity.generateAndSetDate();
        entity.setForm(formEntityService.findEntityById(entity.getForm().getId()));
        loanResultsService.verifyIfScheduled(entity);
        //Quando juntar com o módulo Account
        entity.setAccount(accountRepository.findById(/*getLoggedAccount().getId())*/ 1).orElse(null));
        if (entity.getStatus() == null) {
            entity.setStatus(entity.generateStatus());
        }
        LoanEntity savedEntity = loanEntityRepository.save(entity);

        if (savedEntity.getStatus().getCode() == Status.REVIEW.getCode()) {
            scheduleStatusChange(savedEntity);
        }

        return loanEntityMapper.mapTo(savedEntity);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        LoanEntity entity = loanEntityRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new IllegalArgumentException("Entity with ID " + id + " not found.");
        }

        loanEntityRepository.delete(entity);
    }

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

    @Override
    @Transactional(readOnly = true)
    public List<LoanEntityDto> findLoansByAccountId() {
        Integer id = 1; //Ajustar quando juntar módulos para o id da conta logada
        List<LoanEntity> loans = loanEntityRepository.findByAccountId(id);
        loans.forEach(LoanEntity::updateStatusFinished);
        return loans.stream()
                .map(loanEntityMapper::mapTo)
                .toList();
    }

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

    private void scheduleStatusChange(LoanEntity loanEntity) {
        Instant startTime = Instant.now().plus(Duration.ofSeconds(30)); // 2 minutos no futuro
    
        taskScheduler.schedule(() -> changeStatusRandomly(loanEntity), startTime);
    }

    @Transactional
    private void changeStatusRandomly(LoanEntity loanEntity) {
        Status newStatus = loanEntity.generateStatus();
        loanEntity.setStatus(newStatus);
        loanEntityRepository.save(loanEntity);

        if (newStatus == Status.APPROVED) {
            loanResultsService.sendAmountAccount(loanEntity);
            loanResultsService.verifyIfScheduled(loanEntity);
        }
    }

    @Scheduled(cron = "0 0 0 * * ?") // Executa diariamente à meia-noite
    @Transactional
    void adjustOverdueInstallments() {
        List<LoanEntity> loans = loanEntityRepository.findByStatusPaymentMethod(1, 2);

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

    @Override
    @Transactional(readOnly = true)
    public long getPaidInstallments(LoanEntityDto dto) {
        LoanEntity entity = findEntityById(dto.getId());
        return entity.getNumberOfPaidInstallments();
    }

    public Double getOutstandingBalance(Integer loanId) {
        Double outstandingBalance = loanEntityRepository.getOutstandingBalance(loanId);
        return outstandingBalance;
    }

    public InstallmentEntityDto getFirstUnpaidInstallment(LoanEntityDto dto) {
        LoanEntity entity = loanEntityMapper.mapFrom(dto);
        if (entity == null || entity.getFirstUnpaidInstallment() == null) {
            return null;
        }
        return installmentEntityMapper.mapTo(entity.getFirstUnpaidInstallment());
    }


    public LocalDate getFirstUnpaidInstallmentDate(LoanEntityDto loan) {
        List<InstallmentEntity> installments = loan.getInstallments();

        return installments.stream()
                .filter(installment -> installment.getPaymentDate() == null)
                .map(InstallmentEntity::getDueDate)
                .findFirst()
                .orElse(null);
    }
}