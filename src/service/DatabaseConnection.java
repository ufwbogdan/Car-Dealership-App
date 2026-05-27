package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/dealership";
    private static final String user = "root";
    private static final String password = "admin";

    private static Connection connection;

    private DatabaseConnection() {};

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Database connection successful!");
            }catch (SQLException e) {
                System.out.println("Connection failed: " + e.getMessage());
            }
        }
        return connection;
    }

}
