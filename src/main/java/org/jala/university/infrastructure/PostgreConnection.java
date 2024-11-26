package org.jala.university.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class PostgreConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    private static Map<String, PostgreConnection> instances = new HashMap<>();
    private Connection conn;

    private PostgreConnection(String database) throws SQLException {
        try {
            this.conn = DriverManager.getConnection(URL + "postgres", USER, PASSWORD);
            Statement stmt = conn.createStatement();


            String checkDbExistsQuery = "SELECT 1 FROM pg_database WHERE datname = '" + database + "';";
            var result = stmt.executeQuery(checkDbExistsQuery);

            if (!result.next()) {
                String createDbQuery = "CREATE DATABASE \"" + database + "\";";
                stmt.executeUpdate(createDbQuery);
                System.out.println("Database '" + database + "' created successfully.");
            } else {
                System.out.println("Database '" + database + "' already exists.");
            }


            this.conn = DriverManager.getConnection(URL + database, USER, PASSWORD);
            System.out.println("Successfully connected to the database: " + database);

        } catch (SQLException e) {
            System.err.println("Failed to connect to the database or create the database: " + e.getMessage());
            this.conn = null;
            throw e;
        }
    }

    public static synchronized PostgreConnection getInstance(String database) throws SQLException {
        if (!instances.containsKey(database)) {
            instances.put(database, new PostgreConnection(database));
        }
        return instances.get(database);
    }


    public Connection getConnection() {
        return conn;
    }


    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexão fechada.");
                instances.remove(conn);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public static void closeConnectionFromStaticDatabase(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexão fechada com sucesso.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
