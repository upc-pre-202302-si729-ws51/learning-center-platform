package com.acme.learning.platform.learning.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Embedded
    @Getter
    private LearningPath learningPath;

}
