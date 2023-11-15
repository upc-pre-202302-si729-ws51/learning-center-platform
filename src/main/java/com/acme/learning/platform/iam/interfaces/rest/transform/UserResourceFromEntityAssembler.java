package com.acme.learning.platform.iam.interfaces.rest.transform;

import com.acme.learning.platform.iam.domain.model.aggregates.User;
import com.acme.learning.platform.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream().map(role -> role.getStringName()).toList();
        return new UserResource(user.getId(), user.getUsername(), roles);
    }
}
