package org.jala.university.application.dto.dto_card;

import lombok.Getter;
import lombok.Setter;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/***
 * represention from the purchase
 */
@Getter
@Setter
public class PurchaseRequestDTO {
    private CreditCardDTO creditCard;
    private CreditCardDTO sellerCreditCard;
    private ProductDTO productDTO;
    private String dateBuy;


    public PurchaseRequestDTO(CreditCardDTO creditCard, CreditCardDTO sellerCreditCard, ProductDTO productDTO, String dateBuy) {
        this.creditCard = creditCard;
        this.sellerCreditCard = sellerCreditCard;
        this.productDTO = productDTO;
        this.dateBuy = dateBuy;
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(insertDate());
    }

    public static String insertDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedDate = dateTime.format(formatter);
        Date date = sdf.parse(formattedDate);
        return formattedDate;

    }



}
