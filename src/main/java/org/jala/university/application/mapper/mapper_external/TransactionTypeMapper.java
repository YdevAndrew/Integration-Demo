package org.jala.university.application.mapper.mapper_external;

import org.jala.university.application.dto.dto_external.TransactionTypeDTO;
import org.jala.university.domain.entity.entity_transaction.TransactionTypeEntity;

public class TransactionTypeMapper {

    public TransactionTypeDTO mapTo(TransactionTypeEntity transactionTypeEntity) {
        return TransactionTypeDTO.builder()
                .id(transactionTypeEntity.getId())
                .transactionTypeName(transactionTypeEntity.getTransactionTypeName())
                .build();
    }

    public TransactionTypeEntity mapFrom(TransactionTypeDTO transactionTypeDTO) {
        return TransactionTypeEntity.builder()
                .id(transactionTypeDTO.getId()) // Se o ID for gerado automaticamente, pode ser desnecessário
                .transactionTypeName(transactionTypeDTO.getTransactionTypeName())
                .build();
    }
}