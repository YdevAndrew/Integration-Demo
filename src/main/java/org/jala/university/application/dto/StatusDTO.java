package org.jala.university.application.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.UUID;

@Data
@Builder
@Value
public class StatusDTO {
    Integer id;
    String statusName;
}
