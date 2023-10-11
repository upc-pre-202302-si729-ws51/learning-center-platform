package com.acme.learning.platform.learning.domain.model.aggregates;

import com.acme.learning.platform.learning.domain.model.entities.Course;
import com.acme.learning.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
public class Enrollment extends AbstractAggregateRoot<Enrollment> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Embedded
    private AcmeStudentRecordId acmeStudentRecordId;

    private EnrollmentStatus status;

    // TODO: Add Progress Record

}
