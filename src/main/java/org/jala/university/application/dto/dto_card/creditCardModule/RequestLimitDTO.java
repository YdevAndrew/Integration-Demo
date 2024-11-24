package org.jala.university.application.dto.dto_card.creditCardModule;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class RequestLimitDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_request_limit;
    private BigDecimal value_request;
    private int fk_account_id;

    public RequestLimitDTO(BigDecimal value_request) {
        this.value_request = value_request;
    }
    public RequestLimitDTO() {}
}
