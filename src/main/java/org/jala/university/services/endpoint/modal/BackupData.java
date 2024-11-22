package org.jala.university.services.endpoint.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "backup_data")
public class BackupData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int backup_id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_expiry_date")
    private String cardExpiryDate;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Column(name = "password_credit_card")
    private String passwordCreditCard;

    @Column(name = "client_name")
    private String clientName;


    @Column(name = "payment_date")
    private String paymentDate;

    @Column(name = "limit_value")
    private BigDecimal limitValue;

    @Column(name = "backup_timestamp", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date backupTimestamp = new Date();

    @Column(name = "fk_credit_card")
    private int fkCreditCard;

    @Column(name = "fk_limit_card")
    private int fkLimitCard;

    @Column(name = "fk_payment_date")
    private int fkPaymentDate;

    @Column(name = "fk_client")
    private int fkClient;

    // Construtores
    public BackupData() {}

    public BackupData(String cardNumber, String cardExpiryDate, String cardHolderName, String passwordCreditCard,
                      String clientName, String paymentDate, BigDecimal limitValue,
                      int fkCreditCard, int fkLimitCard, int fkPaymentDate, int fkClient) {
        this.cardNumber = cardNumber;
        this.cardExpiryDate = cardExpiryDate;
        this.cardHolderName = cardHolderName;
        this.passwordCreditCard = passwordCreditCard;
        this.clientName = clientName;
        this.paymentDate = paymentDate;
        this.limitValue = limitValue;
        this.fkCreditCard = fkCreditCard;
        this.fkLimitCard = fkLimitCard;
        this.fkPaymentDate = fkPaymentDate;
        this.fkClient = fkClient;
    }
}
