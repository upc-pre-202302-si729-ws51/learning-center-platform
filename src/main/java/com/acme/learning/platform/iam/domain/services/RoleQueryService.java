package com.acme.learning.platform.iam.domain.services;

import com.acme.learning.platform.iam.domain.model.entities.Role;
import com.acme.learning.platform.iam.domain.model.queries.GetAllRolesQuery;

import java.util.List;

/**
 * This interface represents the query service for the {@link Role} entity.
 * <p>
 *     The query service is responsible for handling the queries related to the role domain entity.
 * </p>
 */
public interface RoleQueryService {

    /**
     * Handle the get all roles query.
     * <p>
     *     This method is responsible for retrieving all the roles from the database.
     * </p>
     * @param query The get all roles query.
     * @return The list of roles.
     * @see GetAllRolesQuery
     */
    List<Role> handle(GetAllRolesQuery query);
}
