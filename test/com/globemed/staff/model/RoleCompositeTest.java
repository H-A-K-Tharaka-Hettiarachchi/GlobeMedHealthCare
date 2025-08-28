package com.globemed.staff.model;

/**
 * Unit test for the Role and Permission composite pattern implementation.
 * This test is written as a plain Java application due to the unavailability of the JUnit framework.
 */
public class RoleCompositeTest {

    public static void main(String[] args) {
        System.out.println("Running RoleCompositeTest...");
        boolean allTestsPassed = true;

        try {
            // Setup
            Role adminRole = new Role("Admin");
            Role staffManagementRole = new Role("Staff Management");
            Permission canCreateStaff = new Permission("staff.create");
            Permission canReadStaff = new Permission("staff.read");
            Permission canUpdateStaff = new Permission("staff.update");
            Permission canDeleteStaff = new Permission("staff.delete");
            Permission canViewLogs = new Permission("logs.view");

            staffManagementRole.addPermission(canCreateStaff);
            staffManagementRole.addPermission(canReadStaff);
            staffManagementRole.addPermission(canUpdateStaff);
            staffManagementRole.addPermission(canDeleteStaff);

            adminRole.addPermission(staffManagementRole);
            adminRole.addPermission(canViewLogs);

            // Test 1: Direct Permission
            if (!adminRole.hasPermission("logs.view")) {
                System.err.println("Test FAILED: Admin should have direct permission to view logs.");
                allTestsPassed = false;
            }

            // Test 2: Inherited Permission
            if (!adminRole.hasPermission("staff.read")) {
                System.err.println("Test FAILED: Admin should have inherited permission to read staff.");
                allTestsPassed = false;
            }

            // Test 3: Non-existent Permission
            if (adminRole.hasPermission("patient.read")) {
                System.err.println("Test FAILED: Admin should not have permission to read patient data.");
                allTestsPassed = false;
            }

            // Test 4: Case-insensitive check
            if (!adminRole.hasPermission("LOGS.VIEW")) {
                System.err.println("Test FAILED: Permission check should be case-insensitive.");
                allTestsPassed = false;
            }

        } catch (Exception e) {
            System.err.println("An exception occurred during the test: " + e.getMessage());
            e.printStackTrace();
            allTestsPassed = false;
        }

        if (allTestsPassed) {
            System.out.println("All tests passed successfully!");
        } else {
            System.err.println("Some tests failed.");
            // In a real scenario, you might exit with a non-zero status code
            // System.exit(1);
        }
    }
}
