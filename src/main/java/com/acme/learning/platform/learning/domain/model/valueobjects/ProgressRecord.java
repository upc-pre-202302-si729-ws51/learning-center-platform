package com.acme.learning.platform.learning.domain.model.valueobjects;

import com.acme.learning.platform.learning.domain.model.aggregates.Enrollment;
import com.acme.learning.platform.learning.domain.model.entities.ProgressRecordItem;
import com.acme.learning.platform.learning.domain.model.entities.Tutorial;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class ProgressRecord {

    @OneToMany(mappedBy = "enrollment")
    private List<ProgressRecordItem> progressRecordItems;

    public ProgressRecord() {
        progressRecordItems = new ArrayList<>();
    }

    public void initializeProgressRecord(Enrollment enrollmentId, LearningPath learningPath) {
        Tutorial tutorialId = learningPath.getFirstTutorialInLearningPath();
        ProgressRecordItem progressRecordItem = new ProgressRecordItem(enrollmentId, tutorialId);
        progressRecordItems.add(progressRecordItem);
    }

    private ProgressRecordItem getProgressRecordItemWithTutorialId(Long tutorialId) {
        return progressRecordItems.stream()
                .filter(progressRecordItem -> progressRecordItem.getTutorial()
                .getId().equals(tutorialId)).findFirst().orElse(null);
    }

    private boolean hasAnItemInProgress() {
        return progressRecordItems.stream()
                .anyMatch(ProgressRecordItem::isInProgress);
    }

    public void startTutorial(Long tutorialId) {
        if (hasAnItemInProgress()) throw new IllegalStateException("A tutorial is already in progress");
        ProgressRecordItem progressRecordItem = getProgressRecordItemWithTutorialId(tutorialId);
        if (progressRecordItem != null) {
            if (progressRecordItem.isNotStarted()) progressRecordItem.start();
            else throw new IllegalStateException("Tutorial with given Id is already started or completed");
        }
        else throw new IllegalArgumentException("Tutorial with given Id not found in progress record");
    }

    public void completeTutorial(Long tutorialId, LearningPath learningPath) {
        ProgressRecordItem progressRecordItem = getProgressRecordItemWithTutorialId(tutorialId);
        if (progressRecordItem != null) progressRecordItem.complete();
        else throw new IllegalArgumentException("Tutorial with given Id not found in progress record");

        if (learningPath.isLastTutorialInLearningPath(tutorialId)) return;

        Tutorial nextTutorial = learningPath.getNextTutorialInLearningPath(tutorialId);
        if (nextTutorial != null) {
            ProgressRecordItem nextProgressRecordItem = new ProgressRecordItem(progressRecordItem.getEnrollment(), nextTutorial);
            progressRecordItems.add(nextProgressRecordItem);
        }
    }

    public long calculateDaysElapsedForEnrollment(Enrollment enrollment) {
        return progressRecordItems.stream().filter(progressRecordItem ->
                        progressRecordItem.getEnrollment().equals(enrollment))
                .mapToLong(ProgressRecordItem::calculateDaysElapsed).sum();
    }
}
