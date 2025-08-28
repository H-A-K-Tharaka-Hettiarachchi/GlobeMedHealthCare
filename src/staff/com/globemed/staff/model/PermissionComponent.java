package com.globemed.staff.model;

/**
 * Component interface for the Composite pattern.
 * Both individual permissions (leaves) and roles (composites) will implement this interface.
 */
public interface PermissionComponent {

    /**
     * Checks if this component grants the specified permission.
     *
     * @param permissionName The name of the permission to check (e.g., "patient.read.all").
     * @return true if the permission is granted, false otherwise.
     */
    boolean hasPermission(String permissionName);

    /**
     * Returns the name of the permission component.
     * For a Permission, it's the permission name. For a Role, it's the role name.
     *
     * @return The name of the component.
     */
    String getName();
}
