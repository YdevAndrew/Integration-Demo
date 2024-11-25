package org.jala.university.infrastructure.persistence_card;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateExpirateDate {
    @Getter @Setter
    private static String expirationDate;

    public static void createExpirateDate() {
        setExpirationDate(calculateExpirationDate());
    }

    public static String calculateExpirationDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateFinal = currentDate.plusYears(5);
        String dataFormate = dateFinal.format(DateTimeFormatter.ofPattern("MM/yyyy"));

        return dataFormate;
    }




    public static void main(String[] args) {
        System.out.println("Data de Expiração: " + calculateExpirationDate());

        /*
        String data = calculateExpirationDate().substring(3, 6);
        String ano = calculateExpirationDate().substring(8);
        System.out.println(data + ano);*/

    }
}
