package org.jala.university.utils.utils_card;


import org.jala.university.application.dto.dto_card.InvoiceDetailsDTO;
import org.jala.university.application.service.service_card.CreditCardService;
import org.jala.university.application.service.service_card.InvoicesService;
import org.jala.university.application.service.service_card.LimitCreditCardService;
import org.jala.university.domain.entity.entity_card.CreditCard;
import org.jala.university.domain.entity.entity_card.Product;
import org.jala.university.domain.entity.entity_card.PurchaseRequest;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class PurchaseService {


       public static boolean makePurchase(PurchaseRequest service, String number_card) throws SQLException {
           double value =  service.getProduct().getProductPrice().doubleValue();
           if(validLimit(number_card) &&  restLimit(number_card) > value){
              return true;
           }else {
               return false;
           }
       }


    public static void main(String[] args) throws SQLException {
        Product product = new Product();
        product.setProductPrice(new BigDecimal("960.82"));
        PurchaseRequest service = new PurchaseRequest();
        service.setProduct(product);


        System.out.println(makePurchase(service, "8414539165402504"));
    }


    public static boolean validLimit(String number_card) throws SQLException {
        double limitCustomer = LimitCreditCardService.getLimitCreditCardInfo(number_card).doubleValue();

        List<InvoiceDetailsDTO> invoices = InvoicesService.getInvoicesDetails(number_card);

        List<InvoiceDetailsDTO> unpaidInvoices = invoices.stream()
                .filter(invoice -> !invoice.getInvoiceStatus())
                .collect(Collectors.toList());

        double totalSpell = unpaidInvoices.stream()
                .mapToDouble(invoice -> invoice.getValueInvoice().doubleValue())
                .sum();

        return limitCustomer > totalSpell;
    }

    public static double restLimit(String number_card) throws SQLException {
        double limitCustomer = LimitCreditCardService.getLimitCreditCardInfo(number_card).doubleValue();

        List<InvoiceDetailsDTO> invoices = InvoicesService.getInvoicesDetails(number_card);

        List<InvoiceDetailsDTO> unpaidInvoices = invoices.stream()
                .filter(invoice -> !invoice.getInvoiceStatus())
                .collect(Collectors.toList());

        double totalSpell = unpaidInvoices.stream()
                .mapToDouble(invoice -> invoice.getValueInvoice().doubleValue())
                .sum();

        double remainingLimit = limitCustomer - totalSpell;

        return Math.round(remainingLimit * 100.0) / 100.0;
    }


    public static void takeInvoiceInformation(String creditNumber) {
        try {
            CreditCard creditCard = CreditCardService.getCreditCardByNumberCard(creditNumber);

            if (creditCard != null) {
                List<InvoiceDetailsDTO> invoices = InvoicesService.getInvoicesDetails(String.valueOf(creditCard.getId_credit_card()));

                invoices.forEach(invoice -> {
                    System.out.println("ID Invoice: " + invoice.getIdInvoice());
                    System.out.println("Total Value: " + invoice.getValueInvoice());
                    System.out.println("Status Invoice: " + invoice.getInvoiceStatus());
                    System.out.println("Number Card: " + invoice.getNumberCard());
                    System.out.println("-------------------");
                });
            } else {
                System.out.println("Credit card not found for number: " + creditNumber);
            }
        } catch (SQLException e) {
            System.err.println("Failed: " + e.getMessage());
        }
    }

    public static void takeInformation(String creditNumber) {
        try {
            CreditCard creditCard = CreditCardService.getCreditCardByNumberCard(creditNumber);

            List<InvoiceDetailsDTO> invoices = InvoicesService.getInvoicesDetails(String.valueOf(creditCard.getId_credit_card()));

            invoices.forEach(invoice -> {
                CreditCard card = CreditCardService.getCreditCardByNumberCard(creditNumber);

                System.out.println("ID Credit Card: " + card.getId_credit_card());
                System.out.println("Number Card: " + card.getNumberCard());
                System.out.println("CVV: " + card.getCvv());
                System.out.println("Expiration Date: " + card.getExpiration_date());
                System.out.println("Active: " + card.isActive());
                System.out.println("Name Card: " + card.getName_card());
                System.out.println("-------------------");
            });
        } catch (SQLException e) {
            System.err.println("Failed: " + e.getMessage());
        }
    }




}
