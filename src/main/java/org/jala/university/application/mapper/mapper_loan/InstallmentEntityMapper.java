package org.jala.university.application.mapper.mapper_loan;

import org.jala.university.application.dto.dto_loan.InstallmentEntityDto;
import org.jala.university.commons.application.mapper.Mapper;
import org.jala.university.domain.entity.entity_loan.InstallmentEntity;
import org.springframework.stereotype.Component;

@Component
public class InstallmentEntityMapper implements Mapper<InstallmentEntity, InstallmentEntityDto> {

    @Override
    public InstallmentEntityDto mapTo(InstallmentEntity entity) {
        return InstallmentEntityDto.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .paid(entity.getPaid())
                .paymentDate(entity.getPaymentDate())
                .DueDate(entity.getDueDate())
                .build();
    }

    @Override
    public InstallmentEntity mapFrom(InstallmentEntityDto dto) {
        return InstallmentEntity.builder()
                .id(dto.getId())
                .amount(dto.getAmount())
                .paid(dto.getPaid())
                .paymentDate(dto.getPaymentDate())
                .dueDate(dto.getDueDate())
                .build();
    }
}
