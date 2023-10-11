package com.acme.learning.platform.learning.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class LearningPathItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne
    private Course course;

    @Getter
    private Long nextItemId;

    @Getter
    @ManyToOne(optional = false)
    private Tutorial tutorial;

    public LearningPathItem(Course course, Tutorial tutorial, Long nextItemId) {
        this.course = course;
        this.tutorial = tutorial;
        this.nextItemId = nextItemId;
    }

    public LearningPathItem() {
        this.tutorial = null;
        this.nextItemId = null;
    }

    public void updateNextItemId(Long nextItemId) {
        this.nextItemId = nextItemId;
    }

}
