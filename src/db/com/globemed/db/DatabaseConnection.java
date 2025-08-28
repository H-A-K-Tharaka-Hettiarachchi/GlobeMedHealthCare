package com.globemed.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages the connection to the MySQL database.
 * This class follows the Singleton pattern to ensure only one connection instance is created.
 */
public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/globemed_healthcare";
    // FIXME: Credentials should be loaded from a secure configuration file, not hardcoded.
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    private static Connection connection = null;

    private DatabaseConnection() {
        // Private constructor to prevent instantiation
    }

    /**
     * Returns the singleton instance of the database connection.
     * If a connection does not exist, it creates one.
     *
     * @return The active database connection.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // The new driver class is `com.mysql.cj.jdbc.Driver`.
                // The old `com.mysql.jdbc.Driver` is deprecated.
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Successfully connected to the database.");
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL JDBC Driver not found.");
                throw new SQLException("MySQL JDBC Driver not found.", e);
            } catch (SQLException e) {
                System.err.println("Failed to connect to the database.");
                throw e; // Re-throw the exception to be handled by the caller
            }
        }
        return connection;
    }

    /**
     * Closes the database connection if it is open.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Failed to close the database connection.");
            }
        }
    }
}
