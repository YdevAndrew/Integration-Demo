package org.jala.university.application.service.service_loan;

import java.math.BigDecimal;
import java.util.List;

import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_external.ScheduledPaymentEntity;
import org.jala.university.domain.entity.entity_loan.InstallmentEntity;
import org.jala.university.domain.entity.entity_loan.LoanEntity;
import org.jala.university.domain.entity.entity_loan.enums.PaymentMethod;
import org.jala.university.domain.repository.repository_account.AccountRepository;
import org.jala.university.domain.repository.repository_loan.LoanEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class LoanResultsServiceImpl implements LoanResultsService {

    @Autowired
    private LoanEntityRepository loanEntityRepository;

    @Autowired
    @Lazy
    private LoanEntityService loanEntityService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ScheduledPaymentRepository scheduledPaymentRepository;

    @Override
    public Account sendAmountAccount(LoanEntity loanEntity) {
        Account account = accountRepository.findById(1 /*colocar o método que pega a conta logada */).orElse(null);
        Account savedAccount;

        if (account == null) {
            return null;
        }
        account.setBalance(account.getBalance().add(BigDecimal.valueOf(loanEntity.getAmountBorrowed())));
        savedAccount = accountRepository.save(account);
        return savedAccount;
    }

    @Override
    @Transactional
    public Account payInstallment(LoanEntity loanEntity) {
        Account account = accountRepository.findById(1/* colocar o método que pega a conta logada */)
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
            loanEntity.getFirstUnpaidInstallment().getAmount()
            )));
        
        return accountRepository.save(account);
    }
    
    @Override
    public void verifyIfScheduled(LoanEntity loanEntity) {
        if (loanEntity.getPaymentMethod().getCode() == PaymentMethod.DEBIT_ACCOUNT.getCode()) {
            schedulePayment(loanEntity);
        }
    }

    // Chama o método de pagamento agendado.
    // O método ainda não está com a lógica completa porque o pagamento
    // agendado ainda não foi feito no módulo de pagamentos externos.
    @Transactional
    boolean schedulePayment(LoanEntity loanEntity) {
        if (/*método != null*/true) {
            //colocar o id que o método retorna
            Integer id = 1;
            loanEntity.setScheduledPayment(scheduledPaymentRepository.findById(id).orElse(null));
            return true;
        }
        return false;
    }

    // Agendamento da verificação a cada 24 horas
    @Scheduled(fixedRate = 86400000)
    void verifyAndUpdatePaidInstallments() {
        List<LoanEntity> loansInReview = loanEntityRepository.findByStatusPaymentMethod(1, 1);

        for (LoanEntity loan : loansInReview) {
            processLoanWithRetry(loan, 3);
        }
    }

    void processLoanWithRetry(LoanEntity loan, int retryCount) {
        for (int attempt = 1; attempt <= retryCount; attempt++) {
            try {
                updateLoanPaidInstallments(loan);
                return;
            } catch (Exception e) {
                System.out.println("Failed processing loan: " + loan.getId() + " - tried: " + attempt + "times");
                if (attempt == retryCount) {
                    System.out.println("Failed processing loan" + loan.getId() + " tried: " + retryCount
                            + " times. Stopping try.");
                }
            }
        }
    }

    // Método para atualizar as parcelas pagas de um empréstimo
    @Transactional 
    void updateLoanPaidInstallments(LoanEntity loanEntity) {
        ScheduledPaymentEntity scheduledPaymentEntity = loanEntity.getScheduledPayment();
        long payments = loanEntityRepository.countCompletedPaymentsForLoan(scheduledPaymentEntity);
        long paidInstallments = loanEntity.getNumberOfPaidInstallments();
        
        if (payments > paidInstallments) {
            long installmentsToMarkAsPaid = payments - paidInstallments;
            LoanEntity loan = loanEntityService.findEntityById(loanEntity.getId());
            loan.markInstallmentsAsPaid(installmentsToMarkAsPaid);
            loanEntityRepository.save(loan);
        }
    }

    // Chama o método de transferir e guardar histórico do módulo de transferência.
    // Para mandar o dinheiro emprestado para a conta.
    // @Override
    // public PaymentHistoryDTO sendAmountAccount(LoanEntity loanEntity) {

    //     Integer bankId = 1;
    //     AccountDto accountBank = accountService.getAccount(bankId);
    //     if (accountBank == null) {
    //         AccountDto accountDto = AccountDto.builder()
    //                 .id(bankId)
    //                 .accountNumber("12345")
    //                 .balance(BigDecimal.valueOf(9999999999999999L))
    //                 .status(AccountStatus.ACTIVE)
    //                 .currency(org.jala.university.domain.entity.Currency.fromCode("USD"))
    //                 .build();
    //         accountBank = accountService.createAccount(accountDto);
    //     }

    //     PaymentHistoryDTO paymentHistoryDTO = PaymentHistoryDTO.builder()
    //             .amount(BigDecimal.valueOf(loanEntity.getAmountBorrowed()))
    //             .transactionDate(LocalDateTime.now())
    //             .agencyReceiver("This")
    //             .accountReceiver(loanEntity.getAccount().getAccountNumber())
    //             .nameReceiver("Loan applicant")
    //             .bankNameReceiver("This")
    //             .build();

    //     return paymentHistoryService.createPaymentHistory(paymentHistoryDTO);
    // }

    // // Chama o método de transferir e guardar histórico do módulo de transferência.
    // // Para pagar a parcela manualmente.
    // @Override
    // public PaymentHistoryDTO payInstallment(LoanEntity loanEntity) {

    //     Integer bankId = 1;
    //     AccountDto accountBank = accountService.getAccount(bankId);
    //     if (accountBank == null) {
    //         AccountDto accountDto = AccountDto.builder()
    //                 .id(bankId)
    //                 .accountNumber("12345")
    //                 .balance(BigDecimal.valueOf(9999999999999999L))
    //                 .status(AccountStatus.ACTIVE)
    //                 .currency(org.jala.university.domain.entity.Currency.fromCode("USD"))
    //                 .build();
    //         accountBank = accountService.createAccount(accountDto);
    //     }

    //     PaymentHistoryDTO paymentHistoryDTO = PaymentHistoryDTO.builder()
    //             .amount(BigDecimal.valueOf(loanEntity.getValueOfInstallments()))
    //             .transactionDate(LocalDateTime.now())
    //             .agencyReceiver("This")
    //             .accountReceiver(accountBank.getAccountNumber())
    //             .nameReceiver("Loan applicant")
    //             .bankNameReceiver("This")
    //             .build();

    //     return paymentHistoryService.createPaymentHistory(paymentHistoryDTO);
    // }
}