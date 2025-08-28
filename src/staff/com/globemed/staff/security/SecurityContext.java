package com.globemed.staff.security;

import com.globemed.staff.model.Role;

/**
 * Holds the security information for the currently authenticated user.
 * This is a simple static implementation suitable for a single-user desktop session.
 */
public final class SecurityContext {

    private static Role currentUserRole;
    private static String currentUsername;
    // In a real app, you'd store the user's ID as well.
    // private static int currentUserId;

    private SecurityContext() {
        // Private constructor to prevent instantiation
    }

    /**
     * Sets the authenticated user's role and username.
     * This should be called upon successful login.
     *
     * @param username The username of the logged-in user.
     * @param role The fully populated Role object for the user.
     */
    public static void setAuthentication(String username, Role role) {
        currentUsername = username;
        currentUserRole = role;
    }

    /**
     * Clears the security context.
     * This should be called on logout.
     */
    public static void clearAuthentication() {
        currentUsername = null;
        currentUserRole = null;
    }

    /**
     * Checks if the current user has the specified permission.
     *
     * @param permissionName The name of the permission to check.
     * @return true if the user is authenticated and has the permission, false otherwise.
     */
    public static boolean hasPermission(String permissionName) {
        if (currentUserRole == null) {
            return false; // No user authenticated
        }
        return currentUserRole.hasPermission(permissionName);
    }

    /**
     * Gets the username of the currently authenticated user.
     *
     * @return The username, or null if no user is authenticated.
     */
    public static String getUsername() {
        return currentUsername;
    }

    /**
     * Checks if a user is currently authenticated.
     *
     * @return true if a user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        return currentUserRole != null;
    }
}
