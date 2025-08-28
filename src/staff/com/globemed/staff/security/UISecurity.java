package com.globemed.staff.security;

import javax.swing.JComponent;

/**
 * A utility class to apply role-based security to UI components.
 */
public final class UISecurity {

    private UISecurity() {
        // Private constructor to prevent instantiation
    }

    /**
     * Applies security to a given Swing component based on a permission name.
     * If the current user (from SecurityContext) has the required permission,
     * the component is enabled and made visible. Otherwise, it is disabled and hidden.
     *
     * @param component The JComponent to apply security to.
     * @param permissionName The name of the permission required to access this component.
     */
    public static void apply(JComponent component, String permissionName) {
        if (SecurityContext.hasPermission(permissionName)) {
            component.setEnabled(true);
            component.setVisible(true);
        } else {
            component.setEnabled(false);
            component.setVisible(false);
        }
    }

    /**
     * A simpler version that just enables or disables a component without hiding it.
     *
     * @param component The JComponent to apply security to.
     * @param permissionName The name of the permission required to use this component.
     */
    public static void setEnabled(JComponent component, String permissionName) {
        if (SecurityContext.hasPermission(permissionName)) {
            component.setEnabled(true);
        } else {
            component.setEnabled(false);
        }
    }
}
