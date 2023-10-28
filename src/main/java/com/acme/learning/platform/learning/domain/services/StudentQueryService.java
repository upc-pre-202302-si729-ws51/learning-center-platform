package com.acme.learning.platform.learning.domain.services;

import com.acme.learning.platform.learning.domain.model.aggregates.Student;
import com.acme.learning.platform.learning.domain.model.queries.GetStudentByAcmeStudentRecordIdQuery;
import com.acme.learning.platform.learning.domain.model.queries.GetStudentByProfileIdQuery;

import java.util.Optional;

public interface StudentQueryService {
    Optional<Student> handle(GetStudentByProfileIdQuery query);
    Optional<Student> handle(GetStudentByAcmeStudentRecordIdQuery query);
}
