package com.acme.learning.platform.learning.interfaces.rest.resources;

public record EnrollmentResource(Long enrollmentId,
                                 String studentRecordId,
                                 Long courseId,
                                 String status) {
}
