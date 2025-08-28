package com.globemed.staff.dao;

import com.globemed.db.DatabaseConnection;
import com.globemed.staff.model.Permission;
import com.globemed.staff.model.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * JDBC implementation of the RoleDAO interface.
 */
public class RoleDAOImpl implements RoleDAO {

    private static final String SELECT_ROLE_BY_ID = "SELECT * FROM role WHERE id = ?";
    private static final String SELECT_ROLE_BY_NAME = "SELECT * FROM role WHERE role = ?";
    private static final String SELECT_PERMISSIONS_BY_ROLE_ID =
        "SELECT p.name FROM permission p " +
        "JOIN role_permission rp ON p.id = rp.permission_id " +
        "WHERE rp.role_id = ?";

    @Override
    public Optional<Role> findRoleByName(String name) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ROLE_BY_NAME)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int roleId = rs.getInt("id");
                    return findRoleById(roleId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding role by name: " + name);
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Role> findRoleById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Step 1: Find the role itself
            Optional<Role> roleOpt = findRoleBase(conn, id);

            if (roleOpt.isPresent()) {
                // Step 2: Populate the role with its permissions
                Role role = roleOpt.get();
                try (PreparedStatement pstmt = conn.prepareStatement(SELECT_PERMISSIONS_BY_ROLE_ID)) {
                    pstmt.setInt(1, id);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            String permissionName = rs.getString("name");
                            role.addPermission(new Permission(permissionName));
                        }
                    }
                }
                return Optional.of(role);
            }
        } catch (SQLException e) {
            System.err.println("Error finding role by id: " + id);
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<Role> findRoleBase(Connection conn, int id) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_ROLE_BY_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String roleName = rs.getString("role");
                    return Optional.of(new Role(roleName));
                }
            }
        }
        return Optional.empty();
    }
}
