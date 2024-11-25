package org.jala.university.application.dao_card;



import org.jala.university.application.service.service_card.LoggedInUser;
import org.jala.university.domain.entity.entity_card.FinancialInfomation;
import org.jala.university.domain.entity.entity_card.PersonalInformation;
import org.jala.university.infrastructure.PostgreConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/***
 * Class responsible for entering the customer's personal and financial information into the database
 */
public class FormInformationDAO {
    /***
     * variable responsible for taking the connection from the PostgreConnection class, which contains the user, password and database
     */
    private static Connection conn;

    static {
        try {
            conn = PostgreConnection.getInstance("jala_bank").getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     *
     * @param conn it is necessary to inform the connection
     */
    public FormInformationDAO(Connection conn) {
        this.conn = conn;
    }

    /***
     * function responsible for adding the customer's information to the database
     * @param informationCustomers are the fields that contain the customer information on the personal form page
     */
    public static void addPersonal(List<PersonalInformation> informationCustomers) {
        String sql = "INSERT INTO personal_information (married_status, cep, street, city, state_, country, customer_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (PersonalInformation info : informationCustomers) {
                stmt.setString(1, info.getMaritalStatus());
                stmt.setString(2, info.getCep());
                stmt.setString(3, info.getStreet());
                stmt.setString(4, info.getCity());
                stmt.setString(5, info.getState());
                stmt.setString(6, info.getCountry());
                stmt.setInt(7, Integer.parseInt(LoggedInUser.getLogInUser()));

                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * function responsible for adding the customer's financial information to the database
     * @param financialInfomation are the fields that contain the customer financial information on the personal form page
     */

    public static void addFinancialInfomation(List<FinancialInfomation> financialInfomation) {
        String sql = "INSERT INTO financial_information (credit_status, income_month, ocupation_status, current_position, customer_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (FinancialInfomation info : financialInfomation) {
                stmt.setString(1, info.getCreditStatus());
                BigDecimal incomeValue = new BigDecimal(info.getMonthIncome());
                stmt.setBigDecimal(2, incomeValue);
                stmt.setString(3, info.getOcupationStatus());
                stmt.setString(4, info.getCurrentPosition());
                stmt.setInt(5, Integer.parseInt(LoggedInUser.getLogInUser()));

                stmt.addBatch();
            }


            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
