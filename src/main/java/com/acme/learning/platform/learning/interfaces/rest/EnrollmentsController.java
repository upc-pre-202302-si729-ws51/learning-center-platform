package com.acme.learning.platform.learning.interfaces.rest;

import com.acme.learning.platform.learning.domain.model.commands.CancelEnrollmentCommand;
import com.acme.learning.platform.learning.domain.model.commands.ConfirmEnrollmentCommand;
import com.acme.learning.platform.learning.domain.model.commands.RejectEnrollmentCommand;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * EnrollmentsController
 * <p>
 *     Enrollment Management Endpoints
 *     <ul>
 *         <li>Request Enrollment</li>
 *         <li>Confirm Enrollment</li>
 *         <li>Reject Enrollment</li>
 *         <li>Cancel Enrollment</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/enrollments", produces = APPLICATION_JSON_VALUE)
public class EnrollmentsController {
    private final EnrollmentCommandService enrollmentCommandService;
    private final EnrollmentQueryService enrollmentQueryService;

    public EnrollmentsController(EnrollmentCommandService enrollmentCommandService, EnrollmentQueryService enrollmentQueryService) {
        this.enrollmentCommandService = enrollmentCommandService;
        this.enrollmentQueryService = enrollmentQueryService;
    }

    /**
     * Request Enrollment
     * @param resource Request Enrollment Resource including the enrollment data
     * @return Enrollment Resource if created, otherwise 400
     * @see EnrollmentResource
     * @see RequestEnrollmentResource
     */
    @PostMapping
    public ResponseEntity<EnrollmentResource> requestEnrollment(@RequestBody RequestEnrollmentResource resource) {
        var command = RequestEnrollmentCommandFromResourceAssembler.toCommandFromResource(resource);
        var enrollmentId = enrollmentCommandService.handle(command);
        if (enrollmentId == 0L) return ResponseEntity.badRequest().build();
        var getEnrollmentByIdQuery = new GetEnrollmentByIdQuery(enrollmentId);
        var enrollment = enrollmentQueryService.handle(getEnrollmentByIdQuery);
        if (enrollment.isEmpty()) return ResponseEntity.badRequest().build();
        var enrollmentResource = EnrollmentResourceFromEntityAssembler.toResourceFromEntity(enrollment.get());
        return new ResponseEntity<>(enrollmentResource, HttpStatus.CREATED);
    }

    /**
     * Confirm Enrollment
     * @param enrollmentId the enrollment id
     * @return Enrollment Identifier if confirmed, otherwise 400
     */
    @PostMapping("/{enrollmentId}/confirmations")
    public ResponseEntity<?> confirmEnrollment(@PathVariable Long enrollmentId) {
        var command = new ConfirmEnrollmentCommand(enrollmentId);
        var confirmedEnrollmentId = enrollmentCommandService.handle(command);
        return ResponseEntity.ok(confirmedEnrollmentId);
    }

    /**
     * Reject Enrollment
     * @param enrollmentId the enrollment id
     * @return Enrollment Identifier if rejected, otherwise 400
     */
    @PostMapping("/{enrollmentId}/rejections")
    public ResponseEntity<?> rejectEnrollment(@PathVariable Long enrollmentId) {
        var command = new RejectEnrollmentCommand(enrollmentId);
        var rejectedEnrollmentId = enrollmentCommandService.handle(command);
        return ResponseEntity.ok(rejectedEnrollmentId);
    }

    /**
     * Cancel Enrollment
     * @param enrollmentId the enrollment id
     * @return Enrollment Identifier if canceled, otherwise 400
     */
    @PostMapping("/enrollments/{enrollmentId}/cancellations")
    public ResponseEntity<?> cancelEnrollment(@PathVariable Long enrollmentId) {
        var command = new CancelEnrollmentCommand(enrollmentId);
        var canceledEnrollmentId = enrollmentCommandService.handle(command);
        return ResponseEntity.ok(canceledEnrollmentId);
    }
}
