package org.jala.university.services;

import org.jala.university.application.dao.CustomerInformationDAO;
import org.jala.university.database.PostgreConnection;

import java.sql.SQLException;


public class LoggedInUser {

    public static void main(String[] args) {
        try {
            String loggedInUserId = getLogInUser();
            System.out.println("Usuário logado com ID: " + loggedInUserId);
        } catch (IllegalArgumentException | SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public static String getLogInUser() throws SQLException {
        String id = "83";
        if (validId(id)) {
            return LoggedUser(id);
        } else {
            throw new IllegalArgumentException("ID não existe no banco de dados.");
        }
    }


    public static boolean validId(String id) throws SQLException {
        PostgreConnection.getInstance("jala_bank").getConnection();
        return CustomerInformationDAO.validId(id);
    }


    public static String LoggedUser(String id) {
        return id;
    }
}
