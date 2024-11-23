package org.jala.university.domain.entity.entity_external;


import org.jala.university.domain.entity.entity_account.Account;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "type")
    private String type;

    @Lob
    private String message;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;


}
