package org.jala.university.application.mapper.mapper_external;

import org.jala.university.application.dto.dto_external.PaymentHistoryDTO;
import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_external.ExternalPaymentHistoryEntity;
import org.jala.university.domain.entity.entity_external.ExternalStatusEntity;
import org.jala.university.domain.entity.entity_external.ExternalTransactionTypeEntity;

public class PaymentHistoryMapper {

    public PaymentHistoryDTO mapTo(ExternalPaymentHistoryEntity paymentHistoryEntity) {
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

    public ExternalPaymentHistoryEntity mapFrom(PaymentHistoryDTO paymentHistoryDTO) {
        ExternalPaymentHistoryEntity paymentHistoryEntity = ExternalPaymentHistoryEntity.builder()
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
        ExternalTransactionTypeEntity transactionType = paymentHistoryDTO.getTransactionType(); // Utiliza a entidade TransactionType diretamente
        ExternalStatusEntity status = paymentHistoryDTO.getStatus(); // Utiliza a entidade Status diretamente

        paymentHistoryEntity.setAccount(account);
        paymentHistoryEntity.setTransactionType(transactionType);
        paymentHistoryEntity.setStatus(status);

        return paymentHistoryEntity;
    }
}
