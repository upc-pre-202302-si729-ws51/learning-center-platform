package com.acme.learning.platform.learning.application.internal.commandservices;

import com.acme.learning.platform.learning.domain.exceptions.CourseNotFoundException;
import com.acme.learning.platform.learning.domain.model.aggregates.Course;
import com.acme.learning.platform.learning.domain.model.aggregates.Enrollment;
import com.acme.learning.platform.learning.domain.model.commands.EnrollStudentInCourseCommand;
import com.acme.learning.platform.learning.domain.services.EnrollmentCommandService;
import com.acme.learning.platform.learning.infrastructure.repositories.CourseRepository;
import com.acme.learning.platform.learning.infrastructure.repositories.EnrollmentRepository;
import com.acme.learning.platform.learning.infrastructure.repositories.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentCommandServiceImpl implements EnrollmentCommandService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentCommandServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Long handle(EnrollStudentInCourseCommand command) {
        studentRepository.findByStudentRecordId(command.studentRecordId().studentRecordId()).map(student -> {
            Course course = courseRepository.findById(command.courseId()).orElseThrow(() -> new CourseNotFoundException(command.courseId()));
            Enrollment enrollment = new Enrollment(command.studentRecordId(), course);
            enrollmentRepository.save(enrollment);
            return enrollment.getId();
        }).orElseThrow(() -> new RuntimeException("Student not found"));
        return 0L;
    }
}
