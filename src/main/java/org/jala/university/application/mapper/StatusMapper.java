package org.jala.university.application.mapper;

import org.jala.university.application.dto.StatusDTO;
import org.jala.university.domain.entity.StatusEntity;

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
