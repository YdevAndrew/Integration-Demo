package org.jala.university.application.mapper.mapper_transaction;


import org.jala.university.application.dto.dto_transaction.PaymentHistoryDTO;
import org.jala.university.domain.entity.entity_transaction.PaymentHistoryEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentHistoryMapper {

    public PaymentHistoryDTO mapTo(PaymentHistoryEntity paymentHistoryEntity) {
        return PaymentHistoryDTO.builder()
                .amount(paymentHistoryEntity.getAmount())
                .cpfReceiver(paymentHistoryEntity.getCpfReceiver())
                .transactionType(paymentHistoryEntity.getTransactionType().getTransactionTypeName())
                .status(paymentHistoryEntity.getStatus().getStatusName())
                .transactionDate(paymentHistoryEntity.getTransactionDate())
                .description(paymentHistoryEntity.getDescription())
                .agencyReceiver(paymentHistoryEntity.getAgencyReceiver())
                .accountReceiver(paymentHistoryEntity.getAccountReceiver())
                .nameReceiver(paymentHistoryEntity.getNameReceiver())
                .bankNameReceiver(paymentHistoryEntity.getBankNameReceiver())
                .build();
    }

    public PaymentHistoryEntity mapFrom(PaymentHistoryDTO paymentHistoryDTO) {
        return PaymentHistoryEntity.builder()
                .amount(paymentHistoryDTO.getAmount())
                .cpfReceiver(paymentHistoryDTO.getCpfReceiver())
                .transactionDate(paymentHistoryDTO.getTransactionDate())
                .description(paymentHistoryDTO.getDescription())
                .agencyReceiver(paymentHistoryDTO.getAgencyReceiver())
                .accountReceiver(paymentHistoryDTO.getAccountReceiver())
                .nameReceiver(paymentHistoryDTO.getNameReceiver())
                .bankNameReceiver(paymentHistoryDTO.getBankNameReceiver())
                .build();
    }
}
