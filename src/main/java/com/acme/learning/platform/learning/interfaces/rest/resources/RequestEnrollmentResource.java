package com.acme.learning.platform.learning.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record RequestEnrollmentResource(
        @NotNull
        String studentRecordId,
        @NotNull
        Long courseId
) {
}
