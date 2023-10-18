package com.acme.learning.platform.learning.domain.model.commands;

import com.acme.learning.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;

public record EnrollStudentInCourseCommand(AcmeStudentRecordId studentRecordId, Long courseId) {

}
