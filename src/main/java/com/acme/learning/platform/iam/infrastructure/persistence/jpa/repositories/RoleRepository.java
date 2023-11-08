package com.acme.learning.platform.iam.infrastructure.persistence.jpa.repositories;

import com.acme.learning.platform.iam.domain.model.entities.Role;
import com.acme.learning.platform.iam.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Role repository
 * <p>
 *     This interface is used to access the role data in the database.
 * </p>
 * @see JpaRepository
 * @see Role
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find role by name
     * @param name role name
     * @return role object if found
     * @see Roles
     */
    Optional<Role> findByName(Roles name);

    /**
     * Check if role exists by name
     * @param name role name
     * @return true if exists
     * @see Roles
     */
    boolean existsByName(Roles name);
}
