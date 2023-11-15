package com.acme.learning.platform.iam.interfaces.rest.transform;

import com.acme.learning.platform.iam.domain.model.entities.Role;
import com.acme.learning.platform.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}
