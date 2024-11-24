package org.jala.university.application.mapper.mapper_external;

import org.jala.university.application.dto.dto_external.StatusDTO;
import org.jala.university.domain.entity.entity_transaction.StatusEntity;

public class StatusMapper {

    public StatusDTO mapTo(StatusEntity statusEntity) {
        return StatusDTO.builder()
                .id(statusEntity.getId())
                .statusName(statusEntity.getStatusName())
                .build();
    }

    public StatusEntity mapFrom(StatusDTO statusDTO) {
        return StatusEntity.builder()
                .id(statusDTO.getId()) // Se o ID for gerado automaticamente, pode ser desnecessário
                .statusName(statusDTO.getStatusName())
                .build();
    }
}
