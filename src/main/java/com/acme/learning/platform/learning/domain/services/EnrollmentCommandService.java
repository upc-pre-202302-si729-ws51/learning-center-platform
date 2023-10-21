package com.acme.learning.platform.learning.domain.services;

import com.acme.learning.platform.learning.domain.model.commands.CancelEnrollmentCommand;
import com.acme.learning.platform.learning.domain.model.commands.ConfirmEnrollmentCommand;
import com.acme.learning.platform.learning.domain.model.commands.RejectEnrollmentCommand;
import com.acme.learning.platform.learning.domain.model.commands.RequestEnrollmentCommand;

public interface EnrollmentCommandService {
    Long handle(RequestEnrollmentCommand command);
    Long handle(ConfirmEnrollmentCommand command);
    Long handle(RejectEnrollmentCommand command);
    Long handle(CancelEnrollmentCommand command);
}

