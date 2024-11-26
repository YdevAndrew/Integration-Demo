package org.jala.university.application.service.service_transaction;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jala.university.application.service.service_account.AccountServiceImpl;
import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_transaction.PaymentHistoryEntity;
import org.jala.university.domain.entity.entity_transaction.StatusEntity;
import org.jala.university.domain.repository.repository_account.AccountRepository;
import org.jala.university.domain.repository.repository_transaction.PaymentHistoryRepository;
import org.jala.university.domain.repository.repository_transaction.StatusRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ScheduledTransactionService {


    private final PaymentHistoryRepository paymentHistoryRepository;
    private final AccountRepository accountRepository;
    private final StatusRepository statusRepository;

    @Transactional
    @Scheduled(fixedRate = 6000)
    public void processScheduledPayments() {

        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        List<PaymentHistoryEntity> paymentsScheduled = paymentHistoryRepository.findAllByStatusAndTransactionDateBetween(returnStatusOption('S'),startOfDay,endOfDay);

        for (PaymentHistoryEntity paymentScheduled : paymentsScheduled) {
            try {
                executePayment(paymentScheduled);
                paymentScheduled.setStatus(returnStatusOption('C'));
            } catch (Exception e) {
                paymentScheduled.setStatus(returnStatusOption('F'));
                System.err.println("Error processing scheduled payment: " + e.getMessage());
            }
            paymentHistoryRepository.save(paymentScheduled);
        }
    }

    public PaymentHistoryEntity executePayment(PaymentHistoryEntity paymentHistoryEntity) {

        Account sender = accountRepository.findById(paymentHistoryEntity.getAccount().getId())
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        if (sender.getBalance().compareTo(paymentHistoryEntity.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        Account receiver = accountRepository.findByAccountNumber(paymentHistoryEntity.getAccountReceiver())
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));

        sender.setBalance(sender.getBalance().subtract(paymentHistoryEntity.getAmount()));
        receiver.setBalance(receiver.getBalance().add(paymentHistoryEntity.getAmount()));

        paymentHistoryEntity.setAccount(sender);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        return paymentHistoryRepository.save(paymentHistoryEntity);
    }


    public void cancelScheduledPayment(Account account, Integer paymentHistoryId ){

        List<PaymentHistoryEntity> paymentsScheduledLists = paymentHistoryRepository.findAllByAccountAndStatus(account, returnStatusOption('S'));

        for (PaymentHistoryEntity paymentHistory : paymentsScheduledLists) {

            if (!(paymentHistory != null && paymentHistory.getId().equals(paymentHistoryId))) {
                System.out.println("Payment not found!!");
                continue;
            }
            paymentHistoryRepository.delete(paymentHistory);
            System.out.println("Payment history deleted successfully.");

        }

    }

    public StatusEntity returnStatusOption(char option){
        if (option == 'C'){
            return statusRepository.findByStatusName("COMPLETED")
                    .orElseThrow(() -> new IllegalArgumentException("Status (COMPLETED) not found"));
        } else if (option == 'F'){
            return statusRepository.findByStatusName("FAILED")
                    .orElseThrow(() -> new IllegalArgumentException("Status (FAILED) not found"));
        }else {
            return statusRepository.findByStatusName("SCHEDULED")
                    .orElseThrow(() -> new IllegalArgumentException("Status (SCHEDULED) not found"));
        }
    }
}
