package com.acme.learning.platform.iam.domain.services;

import com.acme.learning.platform.iam.domain.model.aggregates.User;
import com.acme.learning.platform.iam.domain.model.queries.GetAllUsersQuery;
import com.acme.learning.platform.iam.domain.model.queries.GetUserByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * UserQueryService
 * <p>
 *     Service to handle user queries.
 * </p>
 */
public interface UserQueryService {

    /**
     * Handle the get all users query.
     * <p>
     *     This method is responsible for retrieving all the users from the database.
     * </p>
     * @param query The get all users query.
     * @return The list of users.
     * @see GetAllUsersQuery
     */
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
}
