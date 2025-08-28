package com.globemed.staff;

import com.globemed.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for handling authentication logic.
 */
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * Authenticates an admin user against the database.
     *
     * @param username The username.
     * @param password The password.
     * @return true if the credentials are valid, false otherwise.
     */
    public boolean loginAdmin(String username, String password) {
        // NOTE: This currently uses the deprecated `admin` table.
        // It should be refactored to use the `staff` table with role checks.
        // Also, passwords should be hashed and salted, not stored in plain text.
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";

        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            logger.warn("Login attempt with empty username or password.");
            return false;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    logger.info("Admin user '{}' logged in successfully.", username);
                    return true;
                } else {
                    logger.warn("Failed login attempt for admin user '{}'.", username);
                    return false;
                }
            }
        } catch (SQLException e) {
            logger.error("Database error during admin login for user '{}'.", username, e);
            return false;
        }
    }

    // TODO: Implement login methods for other roles (Doctor, Nurse, etc.)
    // These methods should query the `staff` table and check the `role_id`.
}
