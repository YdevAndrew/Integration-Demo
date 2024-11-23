package org.jala.university.application.service.service_transaction;

import jakarta.transaction.Transactional;
import org.jala.university.application.dto.dto_account.AccountDto;
import org.jala.university.application.dto.dto_transaction.PaymentHistoryDTO;
import org.jala.university.application.mapper.mapper_account.AccountMapper;
import org.jala.university.application.mapper.mapper_transaction.PaymentHistoryMapper;
import org.jala.university.application.service.service_account.AccountService;
import org.jala.university.domain.entity.entity_transaction.PaymentHistoryEntity;
import org.jala.university.domain.repository.repository_account.AccountRepository;
import org.jala.university.domain.repository.repository_transaction.PaymentHistoryRepository;
import org.jala.university.domain.repository.repository_transaction.StatusRepository;
import org.jala.university.domain.repository.repository_transaction.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_transaction.TransactionTypeEntity;
import org.jala.university.domain.entity.entity_transaction.StatusEntity;



import java.time.LocalDateTime;
import java.util.*;




@Service
@Primary
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PaymentHistoryMapper paymentHistoryMapper;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;




    @Override
    @Transactional
    public PaymentHistoryDTO createPaymentHistory(Integer userId, PaymentHistoryDTO paymentHistoryDto, String transactionType) {
        if (paymentHistoryDto.getTransactionDate().isBefore(LocalDateTime.now()) || paymentHistoryDto.getTransactionDate().isEqual(LocalDateTime.now())) {
            System.out.println("Executing immediate transaction logic...");

            Account sender = accountRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

            if (sender.getBalance().compareTo(paymentHistoryDto.getAmount()) < 0) {
                throw new IllegalArgumentException("Insufficient balance");
            }

            Account receiver = accountRepository.findByAccountNumber(paymentHistoryDto.getAccountReceiver())
                    .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));

            sender.setBalance(sender.getBalance().subtract(paymentHistoryDto.getAmount()));
            receiver.setBalance(receiver.getBalance().add(paymentHistoryDto.getAmount()));

            System.out.println("New sender balance: " + sender.getBalance());
            System.out.println("New receiver balance: " + receiver.getBalance());

            PaymentHistoryEntity paymentHistoryEntity = paymentHistoryMapper.mapFrom(paymentHistoryDto);
            paymentHistoryEntity.setAccount(sender);
            paymentHistoryEntity.setTransactionType(returnTransactionType(transactionType));
            paymentHistoryEntity.setStatus(returnStatusScheduledOrCompleted(true));
            paymentHistoryEntity.setTransactionDate(LocalDateTime.now());

            PaymentHistoryEntity savedPaymentHistory = paymentHistoryRepository.save(paymentHistoryEntity);
            accountRepository.save(sender);
            accountRepository.save(receiver);

            return paymentHistoryMapper.mapTo(savedPaymentHistory);

        } else {
            System.out.println("Executing scheduled transaction logic...");

            Account sender = accountRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

            Account receiver = accountRepository.findByAccountNumber(paymentHistoryDto.getAccountReceiver())
                    .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));

            System.out.println("Current sender balance: " + sender.getBalance());
            System.out.println("Current receiver balance: " + receiver.getBalance());

            PaymentHistoryEntity paymentHistoryEntity = paymentHistoryMapper.mapFrom(paymentHistoryDto);
            paymentHistoryEntity.setAccount(sender);
            paymentHistoryEntity.setTransactionType(returnTransactionType("TRANSACTION"));
            paymentHistoryEntity.setStatus(returnStatusScheduledOrCompleted(false));

            PaymentHistoryEntity savedPaymentHistory = paymentHistoryRepository.save(paymentHistoryEntity);

            return paymentHistoryMapper.mapTo(savedPaymentHistory);
        }
    }

    @Override
    public List<AccountDto> getContactList(Integer userId) {
        List<PaymentHistoryEntity> listWithRepetition = paymentHistoryRepository.findAllByAccountId(userId);//implementado para a autenticação

        Set<String> uniqueReceivers = new HashSet<>();
        for (PaymentHistoryEntity payment : listWithRepetition) {
            uniqueReceivers.add(payment.getAccountReceiver());
        }

        List<AccountDto> contactList = new ArrayList<>();

        for (String receiverString : uniqueReceivers) {
            Optional<Account> optionalAccount = accountRepository.findByAccountNumber(receiverString);

            optionalAccount.ifPresent(account -> {
                AccountDto accountDto = AccountMapper.toDto(account);
                contactList.add(accountDto);
            });
        }

        return contactList;
    }

    @Override
    public List<PaymentHistoryDTO> getPaymentHistory(Integer userId){
        List<PaymentHistoryEntity> paymentHistoryEntityListAll = new ArrayList<>();
        List<PaymentHistoryDTO> paymentHistoryListDTO = new ArrayList<>();

        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("account not found"));

        List<PaymentHistoryEntity> paymentHistoryEntityListAccount = paymentHistoryRepository.findAllByAccountId(userId);
        List<PaymentHistoryEntity> paymentHistoryEntityListReceiver = paymentHistoryRepository.findAllByReceiver(account.getAccountNumber());

        paymentHistoryEntityListAll.addAll(paymentHistoryEntityListAccount);
        paymentHistoryEntityListAll.addAll(paymentHistoryEntityListReceiver);

        paymentHistoryEntityListAll.sort(Comparator.comparing(PaymentHistoryEntity::getTransactionDate).reversed());

        for (PaymentHistoryEntity paymentHistoryEntity : paymentHistoryEntityListAll){
            paymentHistoryListDTO.add(paymentHistoryMapper.mapTo(paymentHistoryEntity));
        }

        return paymentHistoryListDTO;
    }

    public List<PaymentHistoryDTO> getPaymentHistoryFiltesSenderOrReceiver(Integer userId, Boolean isSender){

        List<PaymentHistoryEntity> paymentHistoryEntityList;
        List<PaymentHistoryDTO> paymentHistoryListDTO = new ArrayList<>();

        if(isSender){
            paymentHistoryEntityList = paymentHistoryRepository.findAllByAccountId(userId);
            paymentHistoryEntityList.sort(Comparator.comparing(PaymentHistoryEntity::getTransactionDate).reversed());
        }else{

             Account account = accountRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("account not found"));

            paymentHistoryEntityList = paymentHistoryRepository.findAllByReceiver(account.getAccountNumber());
            paymentHistoryEntityList.sort(Comparator.comparing(PaymentHistoryEntity::getTransactionDate).reversed());
        }

        for (PaymentHistoryEntity paymentHistoryEntity : paymentHistoryEntityList){
            paymentHistoryListDTO.add(paymentHistoryMapper.mapTo(paymentHistoryEntity));
        }

        return paymentHistoryListDTO;
    }

    public List<PaymentHistoryDTO> getPaymentHistoryFiltesCompletedOrScheduled(Integer userId, Boolean isCompleted){

        List<PaymentHistoryEntity> paymentHistoryEntityList;
        List<PaymentHistoryDTO> paymentHistoryListDTO = new ArrayList<>();

        paymentHistoryEntityList = paymentHistoryRepository.findAllByAccountIdAndStatus(userId ,returnStatusScheduledOrCompleted(isCompleted));
        paymentHistoryEntityList.sort(Comparator.comparing(PaymentHistoryEntity::getTransactionDate).reversed());

        for (PaymentHistoryEntity paymentHistoryEntity : paymentHistoryEntityList){
            paymentHistoryListDTO.add(paymentHistoryMapper.mapTo(paymentHistoryEntity));
        }

        return paymentHistoryListDTO;
    }


    public StatusEntity returnStatusScheduledOrCompleted(Boolean isCompleted){

        if (isCompleted){
            return statusRepository.findByStatusName("COMPLETED")
                    .orElseThrow(() -> new IllegalArgumentException("Status (COMPLETED) not found"));
        } else {
            return statusRepository.findByStatusName("SCHEDULED")
                    .orElseThrow(() -> new IllegalArgumentException("Status (SCHEDULED) not found"));
        }
    }

    public TransactionTypeEntity returnTransactionType(String transactionType){
         return transactionTypeRepository.findByTransactionTypeName(transactionType)
                    .orElseThrow(() -> new IllegalArgumentException("Status ("+transactionType+") not found"));
    }
}