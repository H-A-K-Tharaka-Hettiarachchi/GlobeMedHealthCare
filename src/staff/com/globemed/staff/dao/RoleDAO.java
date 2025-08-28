package com.globemed.staff.dao;

import com.globemed.staff.model.Role;
import java.util.Optional;

/**
 * Data Access Object interface for Role entities.
 */
public interface RoleDAO {

    /**
     * Finds a role by its name and fully populates it with its permissions.
     * This method should read from the `role`, `permission`, and `role_permission` tables
     * to construct the complete Role object with its composite structure.
     *
     * @param name The name of the role to find.
     * @return an Optional containing the fully populated role if found, otherwise empty.
     */
    Optional<Role> findRoleByName(String name);

    /**
     * Finds a role by its ID and fully populates it with its permissions.
     *
     * @param id The ID of the role to find.
     * @return an Optional containing the fully populated role if found, otherwise empty.
     */
    Optional<Role> findRoleById(int id);

}
