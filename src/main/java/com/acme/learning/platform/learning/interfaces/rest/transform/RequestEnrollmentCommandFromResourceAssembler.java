package com.acme.learning.platform.learning.interfaces.rest.transform;

import com.acme.learning.platform.learning.domain.model.commands.RequestEnrollmentCommand;
import com.acme.learning.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import com.acme.learning.platform.learning.interfaces.rest.resources.RequestEnrollmentResource;

public class RequestEnrollmentCommandFromResourceAssembler {
    public static RequestEnrollmentCommand toCommandFromResource(RequestEnrollmentResource resource) {
        return new RequestEnrollmentCommand(
                new AcmeStudentRecordId(resource.studentRecordId()),
                        resource.courseId());
    }
}
