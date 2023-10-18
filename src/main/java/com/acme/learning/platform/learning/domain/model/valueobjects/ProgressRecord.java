package com.acme.learning.platform.learning.domain.model.valueobjects;

import com.acme.learning.platform.learning.domain.model.aggregates.Enrollment;
import com.acme.learning.platform.learning.domain.model.entities.ProgressRecordItem;
import com.acme.learning.platform.learning.domain.model.entities.Tutorial;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.List;

@Embeddable
public class ProgressRecord {

    @OneToMany
    private List<ProgressRecordItem> progressRecordItems;

    public void initializeProgressRecord(Enrollment enrollmentId, LearningPath learningPath) {
        Tutorial tutorialId = learningPath.getFirstTutorialInLearningPath();
        ProgressRecordItem progressRecordItem = new ProgressRecordItem(enrollmentId, tutorialId);
        progressRecordItems.add(progressRecordItem);
    }
}
