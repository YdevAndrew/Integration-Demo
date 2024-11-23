package org.jala.university.application.mapper.mapper_loan;

import org.jala.university.application.dto.dto_loan.FormEntityDto;
import org.jala.university.commons.application.mapper.Mapper;
import org.jala.university.domain.entity.entity_loan.FormEntity;
import org.springframework.stereotype.Component;

@Component
public class FormEntityMapper implements Mapper<FormEntity, FormEntityDto> {

    @Override
    public FormEntityDto mapTo(FormEntity formEntity) {
        return FormEntityDto.builder()
                .id(formEntity.getId())
                .income(formEntity.getIncome())
                .proofOfIncome(formEntity.getProofOfIncome())
                .maximumAmount(formEntity.getMaximumAmount())
                .build();
    }

    @Override
    public FormEntity mapFrom(FormEntityDto formEntityDto) {
        return FormEntity.builder()
                .id(formEntityDto.getId())
                .income(formEntityDto.getIncome())
                .proofOfIncome(formEntityDto.getProofOfIncome())
                .maximumAmount(formEntityDto.getMaximumAmount())
                .build();
    }
}
