package com.acme.learning.platform.learning.application.internal.queryservices;

import com.acme.learning.platform.learning.domain.model.aggregates.Enrollment;
import com.acme.learning.platform.learning.domain.model.queries.GetAllEnrollmentsQuery;
import com.acme.learning.platform.learning.domain.model.queries.GetCourseEnrollmentsQuery;
import com.acme.learning.platform.learning.domain.model.queries.GetEnrollmentByIdQuery;
import com.acme.learning.platform.learning.domain.model.queries.GetStudentEnrollmentsQuery;
import com.acme.learning.platform.learning.domain.services.EnrollmentQueryService;
import com.acme.learning.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * EnrollmentQueryServiceImpl is the implementation service that handles the queries related to the Enrollment aggregate.
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
@Service
public class EnrollmentQueryServiceImpl implements EnrollmentQueryService {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentQueryServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * @InheritDoc
     */
    @Override
    public List<Enrollment> handle(GetStudentEnrollmentsQuery query) {
        return enrollmentRepository.findAllByAcmeStudentRecordId(query.studentRecordId());
    }

    /**
     * @InheritDoc
     */
    @Override
    public Optional<Enrollment> handle(GetEnrollmentByIdQuery query) {
        return enrollmentRepository.findById(query.enrollmentId());
    }

    /**
     * @InheritDoc
     */
    @Override
    public List<Enrollment> handle(GetCourseEnrollmentsQuery query) {
        return enrollmentRepository.findAllByCourseId(query.courseId());
    }

    /**
     * @InheritDoc
     */
    @Override
    public List<Enrollment> handle(GetAllEnrollmentsQuery query) {
        return enrollmentRepository.findAll();
    }
}
