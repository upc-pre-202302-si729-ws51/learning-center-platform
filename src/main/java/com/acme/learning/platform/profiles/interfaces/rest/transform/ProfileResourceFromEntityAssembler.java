package com.acme.learning.platform.profiles.interfaces.rest.transform;

import com.acme.learning.platform.profiles.domain.model.aggregates.Profile;
import com.acme.learning.platform.profiles.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(entity.getId(), entity.getFullName(), entity.getEmailAddress(), entity.getStreetAddress());
    }
}
