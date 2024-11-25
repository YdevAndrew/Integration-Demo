package org.jala.university.infrastructure.persistence_card;

import lombok.Getter;
import lombok.Setter;
import org.jala.university.application.dao_card.CustomerInformationDAO;

import java.sql.SQLException;

public class AddNameCreditCard {
    @Getter @Setter
    private static String fullName;

    public static void addName() throws SQLException {
        setFullName(getFirstTwoNames());
    }

    public static String searchName() throws SQLException {
        return CustomerInformationDAO.selectCustomer().get(0).getFull_name();
    }
    public static String getFirstTwoNames() throws SQLException {
        String name = searchName();
        String[] nameParts = name.split(" ");
        if (nameParts.length >= 2) {
            return nameParts[0] + " " + nameParts[1];
        } else {
            return name;
        }
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(getFirstTwoNames());
    }

}
