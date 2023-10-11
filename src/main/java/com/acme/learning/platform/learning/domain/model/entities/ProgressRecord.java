package com.acme.learning.platform.learning.domain.model.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.List;

@Embeddable
public class ProgressRecord {

    @OneToMany
    private List<ProgressRecordItem> progressRecordItems;

    public void initializeProgressRecord(Long enrollmentId, LearningPath learningPath) {
        Long tutorialId = learningPath.getFirstTutorialInLearningPath();
        ProgressRecordItem progressRecordItem = new ProgressRecordItem(enrollmentId, tutorialId);
        progressRecordItems.add(progressRecordItem);
    }
}
