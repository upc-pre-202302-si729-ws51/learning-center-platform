package com.acme.learning.platform.iam.interfaces.rest;


import com.acme.learning.platform.iam.domain.model.queries.GetAllUsersQuery;
import com.acme.learning.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.acme.learning.platform.iam.domain.services.UserQueryService;
import com.acme.learning.platform.iam.interfaces.rest.resources.UserResource;
import com.acme.learning.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Users Controller
 * <p>
 *     This controller exposes the endpoints for the user management.
 *     It is responsible for handling the requests and responses for the user management.
 *      Includes the following endpoints:
 *     <ul>
 *         <li>GET /api/v1/users</li>
 *         <li>GET /api/v1/users/{userId}</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {
    private final UserQueryService userQueryService;


    public UsersController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    /**
     * Get all users
     * @return List of users resources if found, otherwise empty list
     */
    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var getAllUserQuery = new GetAllUsersQuery();
        var users = userQueryService.handle(getAllUserQuery);
        var userResources = users.stream().map(UserResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(userResources);
    }

    /**
     * Get user by id
     * @param userId User id
     * @return User resource if found, otherwise not found
     */
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);

    }

}
