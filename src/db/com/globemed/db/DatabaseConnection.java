package com.globemed.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the connection to the MySQL database.
 * This class follows the Singleton pattern to ensure only one connection instance is created.
 */
public class DatabaseConnection {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

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
                logger.info("Successfully connected to the database.");
            } catch (ClassNotFoundException e) {
                logger.error("MySQL JDBC Driver not found.", e);
                throw new SQLException("MySQL JDBC Driver not found.", e);
            } catch (SQLException e) {
                logger.error("Failed to connect to the database.", e);
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
                logger.info("Database connection closed.");
            } catch (SQLException e) {
                logger.error("Failed to close the database connection.", e);
            }
        }
    }
}
