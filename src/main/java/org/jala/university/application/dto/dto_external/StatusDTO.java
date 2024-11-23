package org.jala.university.application.dto.dto_external;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@Value
public class StatusDTO {
    Integer id;
    String statusName;
}