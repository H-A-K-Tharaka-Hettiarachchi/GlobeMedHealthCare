package com.globemed.staff.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Composite class in the Composite pattern.
 * Represents a role, which can contain other roles or individual permissions.
 */
public class Role implements PermissionComponent {

    private final String name;
    private final List<PermissionComponent> children = new ArrayList<>();

    public Role(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be null or empty.");
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Adds a permission component (a role or a permission) to this role.
     *
     * @param component The component to add.
     */
    public void addPermission(PermissionComponent component) {
        children.add(component);
    }

    /**
     * Removes a permission component from this role.
     *
     * @param component The component to remove.
     */
    public void removePermission(PermissionComponent component) {
        children.remove(component);
    }

    @Override
    public boolean hasPermission(String permissionName) {
        // First, check if the role itself matches (e.g., for "role:admin")
        if (this.name.equalsIgnoreCase(permissionName)) {
            return true;
        }

        // If not, check all children recursively.
        for (PermissionComponent component : children) {
            if (component.hasPermission(permissionName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", children=" + children.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
