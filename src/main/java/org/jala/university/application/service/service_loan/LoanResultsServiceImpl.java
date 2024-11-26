package org.jala.university.application.service.service_loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.jala.university.application.dto.dto_loan.LoanEntityDto;
import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_loan.InstallmentEntity;
import org.jala.university.domain.entity.entity_loan.LoanEntity;
import org.jala.university.domain.repository.repository_account.AccountRepository;
import org.jala.university.domain.repository.repository_loan.LoanEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class LoanResultsServiceImpl implements LoanResultsService {

    /**
     * The repository for managing {@link LoanEntity} objects.
     */
    @Autowired
    private LoanEntityRepository loanEntityRepository;

    /**
     * The service for handling loan and related actions.
     */
    @Autowired
    @Lazy
    private LoanEntityService loanEntityService;

    /**
     * The repository for managing {@link Account} objects.
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Account sendAmountAccount(LoanEntity loanEntity) {
        Integer accountId = loanEntity.getAccount().getId();
        Account savedAccount;
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Conta não encontrada"));

        account.setBalance(account.getBalance().add(BigDecimal.valueOf(loanEntity.getAmountBorrowed())));
        savedAccount = accountRepository.save(account);
        return savedAccount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Account payInstallment(LoanEntity loanEntity) {
        Account account = accountRepository.findById(loanEntity.getAccount().getId())
                .orElseThrow(() -> new IllegalStateException("Conta não encontrada"));

        InstallmentEntity firstUnpaidInstallment = loanEntity.getFirstUnpaidInstallment();
        if (firstUnpaidInstallment == null) {
            throw new IllegalStateException("Nenhuma parcela pendente para pagamento.");
        }

        BigDecimal installmentAmount = BigDecimal.valueOf(firstUnpaidInstallment.getAmount());
        if (account.getBalance().compareTo(installmentAmount) < 0) {
            return null;
        }

        account.setBalance(account.getBalance().subtract(BigDecimal.valueOf(
                loanEntity.getFirstUnpaidInstallment().getAmount())));

        return accountRepository.save(account);
    }

    /**
     * {@inheritDoc}
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void processScheduledPayments() {
        List<LoanEntity> loans = loanEntityRepository.findByStatusPaymentMethod(1, 1);
        LocalDate today = LocalDate.now();

        for (LoanEntity loan : loans) {
            List<InstallmentEntity> overdueInstallments = loan.getInstallments().stream()
                    .filter(installment -> (installment.getDueDate().isBefore(today)
                            || installment.getDueDate().isEqual(today)) && !installment.getPaid())
                    .toList();

            for (InstallmentEntity installment : overdueInstallments) {
                boolean success = processInstallmentPayment(installment);

                if (success) {
                    loan.markAsPaidScheduled();
                    loanEntityRepository.save(loan);
                } else {
                    System.err.println("Failed to process payment for installment: " + installment.getId());
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    private boolean processInstallmentPayment(InstallmentEntity installment) {
        LoanEntity loan = installment.getLoan();
        Account account = accountRepository.findById(loan.getAccount().getId()).orElse(null);

        if (account == null) {
            System.err.println("Account not found for loan: " + loan.getId());
            return false;
        }

        if (account.getBalance().compareTo(BigDecimal.valueOf(installment.getAmount())) >= 0) {
            account.setBalance(account.getBalance().subtract(BigDecimal.valueOf(installment.getAmount())));
            accountRepository.save(account);
            return true;
        } else {
            System.err.println("Insufficient balance for account: " + account.getId());
            return false;
        }
    }

}
