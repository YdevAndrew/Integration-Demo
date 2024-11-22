package org.jala.university.services;


import org.jala.university.services.endpoint.dao.CreditCardDAO;

import java.sql.SQLException;

public class CreditCardSearch {

    public static void main(String[] args) throws SQLException {
        getCreditCard();
    }
    public static String getCreditCard() throws SQLException {
        String id = "1";
        if(validId(id)) {
            return IdCrditCard(id);
        }else {
            throw new IllegalArgumentException("ID don't exist");
        }
    }

    public static boolean validId(String id) throws SQLException {

        String id_costumer = LoggedInUser.getLogInUser();
        boolean isValid = false;

        if(CreditCardDAO.validId(id)){
            isValid = true;
        }
        return isValid;
    }





    public static String IdCrditCard(String id){
        return id;
    }

}
