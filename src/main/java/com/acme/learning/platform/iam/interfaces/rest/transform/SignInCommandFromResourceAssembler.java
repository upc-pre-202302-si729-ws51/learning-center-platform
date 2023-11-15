package com.acme.learning.platform.iam.interfaces.rest.transform;

import com.acme.learning.platform.iam.domain.model.commands.SignInCommand;
import com.acme.learning.platform.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}
