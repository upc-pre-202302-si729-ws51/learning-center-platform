package com.acme.learning.platform.learning.domain.services;

import com.acme.learning.platform.learning.domain.model.aggregates.Enrollment;
import com.acme.learning.platform.learning.domain.model.queries.GetAllEnrollmentsQuery;
import com.acme.learning.platform.learning.domain.model.queries.GetCourseEnrollmentsQuery;
import com.acme.learning.platform.learning.domain.model.queries.GetEnrollmentByIdQuery;
import com.acme.learning.platform.learning.domain.model.queries.GetStudentEnrollmentsQuery;
import com.acme.learning.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentRepository;

import java.util.List;
import java.util.Optional;

/** EnrollmentQueryService is the service that handles the queries related to the Enrollment aggregate.
 * It exposes the following methods:
 * <ul>
 *     <li>{@link #handle(GetStudentEnrollmentsQuery)}</li>
 *     <li>{@link #handle(GetEnrollmentByIdQuery)}</li>
 *     <li>{@link #handle(GetCourseEnrollmentsQuery)}</li>
 *     <li>{@link #handle(GetAllEnrollmentsQuery)}</li>
 * </ul>
 * <p>
 *     The service uses the {@link EnrollmentRepository} to retrieve the data.
 * </p>
 */
public interface EnrollmentQueryService {
    /**
     * Returns the enrollments for a given student.
     * @param query the query containing the student id.
     * @return the list of enrollments for the student.
     * @see GetStudentEnrollmentsQuery
     */
    List<Enrollment> handle(GetStudentEnrollmentsQuery query);

    /**
     * Returns the enrollment for a given id.
     * @param query the query containing the enrollment id.
     * @return the enrollment if found, otherwise empty.
     * @see GetEnrollmentByIdQuery
     */
    Optional<Enrollment> handle(GetEnrollmentByIdQuery query);

    /**
     * Returns the enrollments for a given course.
     * @param query the query containing the course id.
     * @return the list of enrollments for the course.
     * @see GetCourseEnrollmentsQuery
     */
    List<Enrollment> handle(GetCourseEnrollmentsQuery query);

    /**
     * Returns all the enrollments.
     * @param query the query.
     * @return the list of enrollments.
     * @see GetAllEnrollmentsQuery
     */
    List<Enrollment> handle(GetAllEnrollmentsQuery query);
}
