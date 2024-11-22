package org.jala.university.services.endpoint.dao;

import org.jala.university.database.PostgreConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * responsible to run the tools about the credit card
 */

public class CreditCardDAO {
    private static Connection conn;

    static {
        try {
            conn = PostgreConnection.getInstance("jala_bank").getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados.", e);
        }
    }



    public static boolean validId(String id) throws SQLException {
        String sql = "SELECT id FROM credit_card WHERE id = ?";

        boolean isValid = false;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    isValid = true;
                }
            }
        }
        return isValid;
    }



}
