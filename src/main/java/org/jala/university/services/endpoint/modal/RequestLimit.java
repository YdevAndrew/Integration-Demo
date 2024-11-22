package org.jala.university.services.endpoint.modal;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "request_limit")
public class RequestLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_request_limit;

    @Column(name = "value_request", nullable = false)
    private BigDecimal value_request;


    @Column(name = "fk_account_id", nullable = false)
    private int fk_account_id;


}
