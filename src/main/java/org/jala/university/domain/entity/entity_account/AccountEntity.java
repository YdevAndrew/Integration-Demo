package org.jala.university.domain.entity.entity_account;
//package org.jala.university.domain.entity;
//
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@Table(name = "account")
//public class AccountEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//
//    @Column(name = "account_number", unique = true)
//    private String accountNumber;
//
//    @Column(name = "balance")
//    private BigDecimal balance;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @Column(name = "payment_password", nullable = false)
//    private String paymentPassword;
//
//    @ManyToOne
//    @JoinColumn(name = "customer_id", nullable = false)
//    private CustomerEntity customer;
//
//
//}
