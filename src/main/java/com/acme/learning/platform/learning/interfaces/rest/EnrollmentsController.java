package com.acme.learning.platform.learning.interfaces.rest;

import com.acme.learning.platform.learning.domain.model.commands.ConfirmEnrollmentCommand;
import com.acme.learning.platform.learning.domain.model.queries.GetEnrollmentByIdQuery;
import com.acme.learning.platform.learning.domain.services.EnrollmentCommandService;
import com.acme.learning.platform.learning.domain.services.EnrollmentQueryService;
import com.acme.learning.platform.learning.interfaces.rest.resources.EnrollmentResource;
import com.acme.learning.platform.learning.interfaces.rest.resources.RequestEnrollmentResource;
import com.acme.learning.platform.learning.interfaces.rest.transform.EnrollmentResourceFromEntityAssembler;
import com.acme.learning.platform.learning.interfaces.rest.transform.RequestEnrollmentCommandFromResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentsController {
    private final EnrollmentCommandService enrollmentCommandService;
    private final EnrollmentQueryService enrollmentQueryService;

    public EnrollmentsController(EnrollmentCommandService enrollmentCommandService, EnrollmentQueryService enrollmentQueryService) {
        this.enrollmentCommandService = enrollmentCommandService;
        this.enrollmentQueryService = enrollmentQueryService;
    }

    @PostMapping
    public ResponseEntity<EnrollmentResource> requestEnrollment(@RequestBody RequestEnrollmentResource resource) {
        var command = RequestEnrollmentCommandFromResourceAssembler.toCommandFromResource(resource);
        var enrollmentId = enrollmentCommandService.handle(command);
        if (enrollmentId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getEnrollmentByIdQuery = new GetEnrollmentByIdQuery(enrollmentId);
        var enrollment = enrollmentQueryService.handle(getEnrollmentByIdQuery);
        if (enrollment.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var enrollmentResource = EnrollmentResourceFromEntityAssembler.toResourceFromEntity(enrollment.get());
        return new ResponseEntity<>(enrollmentResource, HttpStatus.CREATED);
    }

    @PostMapping("/{enrollmentId}/confirmations")
    public ResponseEntity<?> confirmEnrollment(@PathVariable Long enrollmentId) {
        var command = new ConfirmEnrollmentCommand(enrollmentId);
        var confirmedEnrollmentId = enrollmentCommandService.handle(command);
        return ResponseEntity.ok(confirmedEnrollmentId);
    }
}
