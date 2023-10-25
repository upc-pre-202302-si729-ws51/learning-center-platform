package com.acme.learning.platform.profiles.interfaces.rest.transform;

import com.acme.learning.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.acme.learning.platform.profiles.interfaces.rest.resources.CreateProfileResource;

public class CreateProfileCommandFromResourceAssembler {
    public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource) {
        return new CreateProfileCommand(
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.street(),
                resource.number(),
                resource.city(),
                resource.zipCode(),
                resource.country()
        );
    }
}
