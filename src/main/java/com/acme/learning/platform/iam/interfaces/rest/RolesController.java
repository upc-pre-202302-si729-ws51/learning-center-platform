package com.acme.learning.platform.iam.interfaces.rest;

import com.acme.learning.platform.iam.domain.model.queries.GetAllRolesQuery;
import com.acme.learning.platform.iam.domain.services.RoleQueryService;
import com.acme.learning.platform.iam.interfaces.rest.resources.RoleResource;
import com.acme.learning.platform.iam.interfaces.rest.transform.RoleResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Roles Controller
 * <p>Exposes the endpoints for the role management</p>
 * <p>Endpoints:</p>
 * <ul>
 *     <li>GET /api/v1/roles</li>
 * </ul>
 */
@RestController
@RequestMapping(value = "/api/v1/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Roles", description = "Role Management Endpoints")
public class RolesController {
    private final RoleQueryService roleQueryService;

    public RolesController(RoleQueryService roleQueryService) {
        this.roleQueryService = roleQueryService;
    }

    /**
     * Get all roles
     * @return List of roles
     * @see RoleResource
     */
    @GetMapping
    public ResponseEntity<List<RoleResource>> getAllRoles() {
        var getAllRolesQuery = new GetAllRolesQuery();
        var roles = roleQueryService.handle(getAllRolesQuery);
        var roleResources = roles.stream().map(RoleResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(roleResources);
    }
}
