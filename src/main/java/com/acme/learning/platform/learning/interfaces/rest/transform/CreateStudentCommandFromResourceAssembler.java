package com.acme.learning.platform.learning.interfaces.rest.transform;

import com.acme.learning.platform.learning.domain.model.commands.CreateStudentCommand;
import com.acme.learning.platform.learning.interfaces.rest.resources.CreateStudentResource;

public class CreateStudentCommandFromResourceAssembler {
    public static CreateStudentCommand toCommandFromResource(CreateStudentResource resource) {
        return new CreateStudentCommand(
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
