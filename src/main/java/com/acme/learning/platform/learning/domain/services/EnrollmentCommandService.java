package com.acme.learning.platform.learning.domain.services;

import com.acme.learning.platform.learning.domain.model.commands.RequestEnrollmentCommand;

public interface EnrollmentCommandService {
    Long handle(RequestEnrollmentCommand command);
}

