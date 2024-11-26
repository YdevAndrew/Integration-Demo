package org.jala.university.application.dao_card;


import org.jala.university.application.dto.dto_card.accountModule.CustomerBankDTO;
import org.jala.university.application.service.service_card.LoggedInUser;
import org.jala.university.infrastructure.PostgreConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerInformationDAO {
    private static Connection conn;

    static {
        try {
            conn = PostgreConnection.getInstance("jala_bank").getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: ", e);
        }
    }

    public CustomerInformationDAO(Connection conn) {
        this.conn = conn;
    }


    public static boolean validId(String id) {
        String sql = "SELECT id FROM customer WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao validar ID do cliente: ", e);
        }
    }


    public static List<CustomerBankDTO> selectCustomer() throws SQLException {
        List<CustomerBankDTO> infoCustomer = new ArrayList<>();
        String sql = "SELECT id, full_name, cpf, gender, birthday, email " +
                "FROM customer " +
                "WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(LoggedInUser.LoggedUser()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CustomerBankDTO customer = new CustomerBankDTO(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getString("cpf"),
                            rs.getString("gender"),
                            rs.getDate("birthday"),
                            rs.getString("email")
                    );
                    infoCustomer.add(customer);
                }
            }
        }
        return infoCustomer;
    }

    public static void main(String[] args) {
        try {
            List<CustomerBankDTO> infoCustomer = selectCustomer();

            for (CustomerBankDTO customer : infoCustomer) {
                System.out.println(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
