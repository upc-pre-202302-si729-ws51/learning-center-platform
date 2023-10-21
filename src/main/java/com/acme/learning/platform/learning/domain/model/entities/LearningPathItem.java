package com.acme.learning.platform.learning.domain.model.entities;

import com.acme.learning.platform.learning.domain.model.aggregates.Course;
import com.acme.learning.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * Represents a learning path item in the learning path of a course.
 */

@Entity
public class LearningPathItem extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    @Getter
    @ManyToOne
    @JoinColumn(name = "next_item_id")
    private LearningPathItem nextItem;

    @Getter
    @ManyToOne(optional = false)
    @JoinColumn(name = "tutorial_id")
    private Tutorial tutorial;

    public LearningPathItem(Course course, Tutorial tutorial, LearningPathItem nextItem) {
        this.course = course;
        this.tutorial = tutorial;
        this.nextItem = nextItem;
    }

    public LearningPathItem() {
        this.tutorial = null;
        this.nextItem = null;
    }

    public void updateNextItem(LearningPathItem nextItem) {
        this.nextItem = nextItem;
    }

}
