package com.acme.learning.platform.learning.domain.model.aggregates;

import com.acme.learning.platform.learning.domain.model.valueobjects.LearningPath;
import com.acme.learning.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;

/**
 *  Course aggregate
 *  Represents a course in the system
 *  Contains a learning path
 */
@Entity
public class Course extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Embedded
    @Getter
    private LearningPath learningPath;

}
