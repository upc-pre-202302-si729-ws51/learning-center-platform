package com.acme.learning.platform.iam.infrastructure.persistence.jpa.repositories;

import com.acme.learning.platform.iam.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User repository
 * <p>
 *     This interface is used to access the user data from the database.
 * </p>
 * @see JpaRepository
 * @see User
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find user by username
     * @param username the username to search
     * @return user object if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if user exists by username
     * @param username the username to search
     * @return true if user exists
     */
    boolean existsByUsername(String username);
}
