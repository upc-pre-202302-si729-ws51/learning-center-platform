package com.acme.learning.platform.learning.interfaces.rest;

import com.acme.learning.platform.learning.domain.model.queries.GetCourseEnrollmentsQuery;
import com.acme.learning.platform.learning.domain.services.EnrollmentQueryService;
import com.acme.learning.platform.learning.interfaces.rest.resources.EnrollmentResource;
import com.acme.learning.platform.learning.interfaces.rest.transform.EnrollmentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * CourseEnrollmentsController
 * <p>
 *      REST controller for managing the Course Enrollments resource.
 *     This controller exposes the following endpoints:
 *     <ul>
 *         <li>GET /api/v1/courses/{courseId}/enrollments: Get Course Enrollments</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/courses/{courseId}/enrollments", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Courses")
public class CourseEnrollmentsController {
    private final EnrollmentQueryService enrollmentQueryService;

    public CourseEnrollmentsController(EnrollmentQueryService enrollmentQueryService) {
        this.enrollmentQueryService = enrollmentQueryService;
    }

    /**
     * Get Course Enrollments
     * @param courseId the course id.
     * @return the list of enrollment resources for the course.
     * @see EnrollmentResource
     */
    @GetMapping
    public ResponseEntity<List<EnrollmentResource>> getCourseEnrollments(@PathVariable Long courseId) {
        var getCourseEnrollmentsQuery = new GetCourseEnrollmentsQuery(courseId);
        var enrollments = enrollmentQueryService.handle(getCourseEnrollmentsQuery);
        var enrollmentResources = enrollments.stream().map(EnrollmentResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(enrollmentResources);
    }

}
