package org.jala.university.application.mapper;

import org.jala.university.application.dto.PaymentHistoryDTO;
import org.jala.university.domain.entity.Account;
import org.jala.university.domain.entity.PaymentHistoryEntity;
//import org.jala.university.domain.entity.AccountEntity;
import org.jala.university.domain.entity.TransactionTypeEntity;
import org.jala.university.domain.entity.StatusEntity;

public class PaymentHistoryMapper {

    public PaymentHistoryDTO mapTo(PaymentHistoryEntity paymentHistoryEntity) {
        return PaymentHistoryDTO.builder()
                .id(paymentHistoryEntity.getId())
                .accountId(paymentHistoryEntity.getAccount()) // Mantém a referência à entidade Account
                .amount(paymentHistoryEntity.getAmount())
                .cpfReceiver(paymentHistoryEntity.getCpfReceiver())
                .cnpjReceiver(paymentHistoryEntity.getCnpjReceiver())
                .transactionDate(paymentHistoryEntity.getTransactionDate())
                .description(paymentHistoryEntity.getDescription())
                .expiredDate(paymentHistoryEntity.getExpiredDate())
                .transactionType(paymentHistoryEntity.getTransactionType()) // Mantém a referência à entidade TransactionType
                .agencyReceiver(paymentHistoryEntity.getAgencyReceiver())
                .accountReceiver(paymentHistoryEntity.getAccountReceiver())
                .nameReceiver(paymentHistoryEntity.getNameReceiver())
                .bankNameReceiver(paymentHistoryEntity.getBankNameReceiver())
                .status(paymentHistoryEntity.getStatus()) // Mantém a referência à entidade Status
                .build();
    }

    public PaymentHistoryEntity mapFrom(PaymentHistoryDTO paymentHistoryDTO) {
        PaymentHistoryEntity paymentHistoryEntity = PaymentHistoryEntity.builder()
                .amount(paymentHistoryDTO.getAmount())
                .transactionDate(paymentHistoryDTO.getTransactionDate())
                .description(paymentHistoryDTO.getDescription())
                .expiredDate(paymentHistoryDTO.getExpiredDate())
                .agencyReceiver(paymentHistoryDTO.getAgencyReceiver())
                .accountReceiver(paymentHistoryDTO.getAccountReceiver())
                .nameReceiver(paymentHistoryDTO.getNameReceiver())
                .bankNameReceiver(paymentHistoryDTO.getBankNameReceiver())
                .cpfReceiver(paymentHistoryDTO.getCpfReceiver())
                .cnpjReceiver(paymentHistoryDTO.getCnpjReceiver())
                .build();

        // Configurar as referências às entidades relacionadas
        Account account = paymentHistoryDTO.getAccountId(); // Utiliza a entidade Account diretamente
        TransactionTypeEntity transactionType = paymentHistoryDTO.getTransactionType(); // Utiliza a entidade TransactionType diretamente
        StatusEntity status = paymentHistoryDTO.getStatus(); // Utiliza a entidade Status diretamente

        paymentHistoryEntity.setAccount(account);
        paymentHistoryEntity.setTransactionType(transactionType);
        paymentHistoryEntity.setStatus(status);

        return paymentHistoryEntity;
    }
}
