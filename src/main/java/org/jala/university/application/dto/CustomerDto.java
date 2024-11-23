package org.jala.university.application.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;


import java.util.UUID;

@Data
@Builder
@Value
public class CustomerDTO {
    Integer id;
    String fullName;
    String cpf;
}
