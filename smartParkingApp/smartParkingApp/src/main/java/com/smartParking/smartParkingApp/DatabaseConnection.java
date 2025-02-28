package com.smartParking.smartParkingApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String url = "jdbc:mysql://localhost:3306/smartparking"; // JDBC URL for MySQL database
    private static final String user = "root"; 									// MySQL username
    private static final String password = "admin"; 							// MySQL password

    // Establish and return a connection to MySQL database
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC driver
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }

        // Establish connection to database using URL, username, and password
        return DriverManager.getConnection(url, user, password);
    }

    // Main method to test the connection
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("\nConnection established successfully!");
            } else {
                System.out.println("\nFailed to establish connection.");
            }
        } catch (SQLException e) {
            System.out.println("\nConnection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
