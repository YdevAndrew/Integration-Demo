package org.jala.university.application.mapper.mapper_loan;

import org.jala.university.application.dto.dto_loan.FormEntityDto;
import org.jala.university.commons.application.mapper.Mapper;
import org.jala.university.domain.entity.entity_loan.FormEntity;
import org.springframework.stereotype.Component;

/**
 * This class implements the {@link Mapper} interface to provide mapping
 * functionality between {@link FormEntity} and {@link FormEntityDto}.
 */
@Component
public class FormEntityMapper implements Mapper<FormEntity, FormEntityDto> {

    /**
     * Maps a {@link FormEntity} object to a {@link FormEntityDto} object.
     *
     * @param formEntity The {@link FormEntity} object to map from.
     * @return The mapped {@link FormEntityDto} object.
     */
    @Override
    public FormEntityDto mapTo(FormEntity formEntity) {
        return FormEntityDto.builder()
                .id(formEntity.getId())
                .income(formEntity.getIncome())
                .proofOfIncome(formEntity.getProofOfIncome())
                .maximumAmount(formEntity.getMaximumAmount())
                .build();
    }

    /**
     * Maps a {@link FormEntityDto} object to a {@link FormEntity} object.
     *
     * @param formEntityDto The {@link FormEntityDto} object to map from.
     * @return The mapped {@link FormEntity} object.
     */
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
