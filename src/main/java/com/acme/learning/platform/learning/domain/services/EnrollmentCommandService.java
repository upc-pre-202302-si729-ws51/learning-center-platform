package com.acme.learning.platform.learning.domain.services;

import com.acme.learning.platform.learning.domain.model.commands.EnrollStudentInCourseCommand;

public interface EnrollmentCommandService {
    Long handle(EnrollStudentInCourseCommand command);
}

