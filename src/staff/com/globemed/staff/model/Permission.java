package com.globemed.staff.model;

import java.util.Objects;

/**
 * Leaf class in the Composite pattern.
 * Represents a single permission.
 */
public class Permission implements PermissionComponent {

    private final String name;

    public Permission(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Permission name cannot be null or empty.");
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasPermission(String permissionName) {
        return this.name.equalsIgnoreCase(permissionName);
    }

    @Override
    public String toString() {
        return "Permission{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
