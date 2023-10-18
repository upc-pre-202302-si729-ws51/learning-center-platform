package com.acme.learning.platform.learning.domain.model.queries;

import com.acme.learning.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;

public record GetStudentEnrollmentsQuery(AcmeStudentRecordId studentRecordId) {
}
