package org.jala.university.application.mapper.mapper_external;

import org.jala.university.application.dto.dto_external.ScheduledPaymentDTO;
import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_external.ScheduledPaymentEntity;

public class ScheduledPaymentMapper {

    public ScheduledPaymentDTO mapTo(ScheduledPaymentEntity scheduledPaymentEntity) {
        return ScheduledPaymentDTO.builder()
                .id(scheduledPaymentEntity.getId())
                .account(scheduledPaymentEntity.getAccount()) // Mantém a referência à entidade Account
                .description(scheduledPaymentEntity.getDescription())
                .amount(scheduledPaymentEntity.getAmount())
                .cpfReceiver(scheduledPaymentEntity.getCpfReceiver())
                .cnpjReceiver(scheduledPaymentEntity.getCnpjReceiver())
                .accountReceiver(scheduledPaymentEntity.getAccountReceiver())
                .agencyReceiver(scheduledPaymentEntity.getAgencyReceiver())
                .expiredDate(scheduledPaymentEntity.getExpiredDate())
                .startDate(scheduledPaymentEntity.getStartDate())
                .endDate(scheduledPaymentEntity.getEndDate())
                .transactionDate(scheduledPaymentEntity.getTransactionDate())
                .build();
    }

    public ScheduledPaymentEntity mapFrom(ScheduledPaymentDTO scheduledPaymentDTO) {
        ScheduledPaymentEntity scheduledPaymentEntity = ScheduledPaymentEntity.builder()
                .description(scheduledPaymentDTO.getDescription())
                .amount(scheduledPaymentDTO.getAmount())
                .cpfReceiver(scheduledPaymentDTO.getCpfReceiver())
                .cnpjReceiver(scheduledPaymentDTO.getCnpjReceiver())
                .accountReceiver(scheduledPaymentDTO.getAccountReceiver())
                .agencyReceiver(scheduledPaymentDTO.getAgencyReceiver())
                .expiredDate(scheduledPaymentDTO.getExpiredDate())
                .startDate(scheduledPaymentDTO.getStartDate())
                .endDate(scheduledPaymentDTO.getEndDate())
                .transactionDate(scheduledPaymentDTO.getTransactionDate())
                .build();

        // Configurar a referência à entidade Account aa
        Account account = scheduledPaymentDTO.getAccount(); // Utiliza a entidade Account diretamente
        scheduledPaymentEntity.setAccount(account);

        return scheduledPaymentEntity;
                }
}