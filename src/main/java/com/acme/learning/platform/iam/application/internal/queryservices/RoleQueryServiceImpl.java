package com.acme.learning.platform.iam.application.internal.queryservices;

import com.acme.learning.platform.iam.domain.model.entities.Role;
import com.acme.learning.platform.iam.domain.model.queries.GetAllRolesQuery;
import com.acme.learning.platform.iam.domain.services.RoleQueryService;
import com.acme.learning.platform.iam.infrastructure.persistence.jpa.repositories.RoleRepository;

import java.util.List;

/**
 * Role query service implementation
 * <p>
 *     This class is used to handle the role queries.
 * </p>
 * @see RoleQueryService
 * @see RoleRepository
 */
public class RoleQueryServiceImpl implements RoleQueryService {
    private final RoleRepository roleRepository;

    public RoleQueryServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * @InheritDoc
     */
    @Override
    public List<Role> handle(GetAllRolesQuery query) {
        return roleRepository.findAll();
    }
}
