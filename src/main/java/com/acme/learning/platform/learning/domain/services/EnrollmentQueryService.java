package com.acme.learning.platform.learning.domain.services;

import com.acme.learning.platform.learning.domain.model.aggregates.Enrollment;
import com.acme.learning.platform.learning.domain.model.queries.GetStudentEnrollmentsQuery;

import java.util.List;

public interface EnrollmentQueryService {
    List<Enrollment> handle(GetStudentEnrollmentsQuery query);
}
