package com.globemed.staff;

import com.globemed.db.DatabaseConnection;
import com.globemed.staff.dao.RoleDAO;
import com.globemed.staff.dao.RoleDAOImpl;
import com.globemed.staff.model.Role;
import com.globemed.staff.security.SecurityContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Controller for handling authentication logic.
 */
public class AuthController {

    private final RoleDAO roleDAO;

    public AuthController() {
        this.roleDAO = new RoleDAOImpl();
    }

    /**
     * Authenticates a user against the `staff` table.
     * On success, it populates the SecurityContext with the user's role and permissions.
     *
     * @param username The username.
     * @param password The password.
     * @return true if the credentials are valid, false otherwise.
     */
    public boolean login(String username, String password) {
        // FIXME: Passwords should be hashed and salted, not stored in plain text.
        // This query is for demonstration purposes only.
        String sql = "SELECT role_id FROM staff WHERE username = ? AND password = ? AND status_id = 1"; // Assuming status_id 1 is 'Active'

        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            System.out.println("Login attempt with empty username or password.");
            return false;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int roleId = rs.getInt("role_id");
                    System.out.println(String.format("User '%s' authenticated successfully with role_id: %d", username, roleId));

                    // Now, fetch the role and its permissions
                    Optional<Role> roleOpt = roleDAO.findRoleById(roleId);
                    if (roleOpt.isPresent()) {
                        SecurityContext.setAuthentication(username, roleOpt.get());
                        System.out.println(String.format("SecurityContext populated for user '%s' with role '%s'.", username, roleOpt.get().getName()));
                        return true;
                    } else {
                        System.err.println("Could not find role definition for role_id: " + roleId + ". Login failed.");
                        return false;
                    }
                } else {
                    System.out.println("Failed login attempt for user '" + username + "'.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error during login for user '" + username + "'.");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Logs the current user out by clearing the security context.
     */
    public void logout() {
        System.out.println("User '" + SecurityContext.getUsername() + "' logging out.");
        SecurityContext.clearAuthentication();
    }
}
