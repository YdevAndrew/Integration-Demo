package org.jala.university.application.dto.dto_card;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class InvoiceDetailsDTO {
    private Integer idInvoice;
    private Integer fkAccount;
    private String numberCard;
    private Integer numberOfInstallments;
    private Boolean invoiceStatus;
    private BigDecimal totalValue;
    private String datePayment;
    private Boolean paymentStatus;
    private BigDecimal valueInvoice;
    private String productName;
    private String productDescription;
    private String purchaseDate;
    private Integer sellerCardId;
    private String sellerCreditCard;
    private String barcode;

    public InvoiceDetailsDTO(Integer idInvoice, Integer fkAccount, String numberCard,
                             Integer numberOfInstallments, Boolean invoiceStatus, BigDecimal totalValue,
                             String datePayment, Boolean paymentStatus, BigDecimal valueInvoice) {
        this.idInvoice = idInvoice;
        this.fkAccount = fkAccount;
        this.numberCard = numberCard;
        this.numberOfInstallments = numberOfInstallments;
        this.invoiceStatus = invoiceStatus;
        this.totalValue = totalValue;
        this.datePayment = datePayment;
        this.paymentStatus = paymentStatus;
        this.valueInvoice = valueInvoice;
    }

    public InvoiceDetailsDTO(Integer idInvoice, Integer fkAccount, String numberCard, Integer numberOfInstallments,
                             Boolean invoiceStatus, BigDecimal totalValue, String datePayment, Boolean paymentStatus,
                             BigDecimal valueInvoice, String productName, String productDescription, String purchaseDate,
                             Integer sellerCardId, String sellerCreditCard) {
        this.idInvoice = idInvoice;
        this.fkAccount = fkAccount;
        this.numberCard = numberCard;
        this.numberOfInstallments = numberOfInstallments;
        this.invoiceStatus = invoiceStatus;
        this.totalValue = totalValue;
        this.datePayment = datePayment;
        this.paymentStatus = paymentStatus;
        this.valueInvoice = valueInvoice;
        this.productName = productName;
        this.productDescription = productDescription;
        this.purchaseDate = purchaseDate;
        this.sellerCardId = sellerCardId;
        this.sellerCreditCard = sellerCreditCard;  // Inicializa o novo campo
    }


    public InvoiceDetailsDTO(Integer idInvoice, Integer fkAccount, String numberCard, Integer numberOfInstallments,
                             Boolean invoiceStatus, BigDecimal totalValue, String datePayment, Boolean paymentStatus,
                             BigDecimal valueInvoice, String productName, String productDescription, String purchaseDate,
                             Integer sellerCardId, String sellerCreditCard, String barcode) {
        this.idInvoice = idInvoice;
        this.fkAccount = fkAccount;
        this.numberCard = numberCard;
        this.numberOfInstallments = numberOfInstallments;
        this.invoiceStatus = invoiceStatus;
        this.totalValue = totalValue;
        this.datePayment = datePayment;
        this.paymentStatus = paymentStatus;
        this.valueInvoice = valueInvoice;
        this.productName = productName;
        this.productDescription = productDescription;
        this.purchaseDate = purchaseDate;
        this.sellerCardId = sellerCardId;
        this.sellerCreditCard = sellerCreditCard;
        this.barcode = barcode;
    }


    @Override
    public String toString() {
        return "InvoiceDetailsDTO{" +
                "idInvoice=" + idInvoice +
                ", fkAccount=" + fkAccount +
                ", numberCard='" + numberCard + '\'' +
                ", numberOfInstallments=" + numberOfInstallments +
                ", invoiceStatus=" + invoiceStatus +
                ", totalValue=" + totalValue +
                ", datePayment='" + datePayment + '\'' +
                ", paymentStatus=" + paymentStatus +
                ", valueInvoice=" + valueInvoice +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", sellerCardId=" + sellerCardId +
                ", sellerCreditCard='" + sellerCreditCard + '\'' +  // Adiciona o número do cartão do seller no toString
                '}';
    }
}
