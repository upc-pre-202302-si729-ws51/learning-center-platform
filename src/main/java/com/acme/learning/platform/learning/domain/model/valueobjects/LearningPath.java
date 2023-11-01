package com.acme.learning.platform.learning.domain.model.valueobjects;

import com.acme.learning.platform.learning.domain.model.aggregates.Course;
import com.acme.learning.platform.learning.domain.model.entities.LearningPathItem;
import com.acme.learning.platform.learning.domain.model.entities.Tutorial;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a learning path of tutorials in a course.
 * A learning path is a linked list of tutorials.
 *
 */
@Embeddable
public class LearningPath {

    @OneToMany(mappedBy = "course")
    private List<LearningPathItem> learningPathItems;

    public LearningPath() {
        this.learningPathItems = new ArrayList<>();
    }

    /**
     * Adds the item before the item with the given id
     * @param course The course that owns the learning path
     * @param tutorial The tutorial to add
     * @param nextItem The id of the item before which the new item should be added
     */
    public void addItem(Course course, Tutorial tutorial, LearningPathItem nextItem) {
        LearningPathItem learningPathItem = new LearningPathItem(course, tutorial, nextItem);
        this.learningPathItems.add(learningPathItem);
    }

    /***
     * Adds the item at the end of the learning path
     * @param course The course that owns the learning path
     * @param tutorial The tutorial to add
     */
    public void addItem(Course course, Tutorial tutorial) {
        LearningPathItem originalLastItem = getLastItemInLearningPath();

        LearningPathItem learningPathItem = new LearningPathItem(course, tutorial, null);
        learningPathItems.add(learningPathItem);
        if (originalLastItem != null) originalLastItem.updateNextItem(learningPathItem);
    }


    public void addItem(Course course, Tutorial tutorial, Long nextTutorialId) {
        LearningPathItem nextItem = getLearningPathItemWithTutorialId(nextTutorialId);
        addItem(course, tutorial, nextItem);
    }
    public Long getFirstTutorialIdInLearningPath() {
        return learningPathItems.get(0).getTutorial().getId();
    }

    public Tutorial getFirstTutorialInLearningPath() {
        return learningPathItems.get(0).getTutorial();
    }

    public Tutorial getNextTutorialInLearningPath(Long currentTutorialId) {
        LearningPathItem item = getLearningPathItemWithTutorialId(currentTutorialId).getNextItem();
        return item != null ? item.getTutorial() : null;
    }

    public boolean isLastTutorialInLearningPath(Long currentTutorialId) {
        return getLearningPathItemWithTutorialId(currentTutorialId).getNextItem() == null;
    }
    private LearningPathItem getLearningPathItemWithId(Long itemId) {
        return learningPathItems.stream()
                .filter(learningPathItem -> learningPathItem.getId().equals(itemId))
                .findFirst().orElse(null);
    }

    private LearningPathItem getLearningPathItemWithTutorialId(Long tutorialId) {
        return learningPathItems.stream()
                .filter(learningPathItem -> learningPathItem.getTutorial().getId().equals(tutorialId))
                .findFirst().orElse(null);
    }

    private LearningPathItem getLastItemInLearningPath() {
        return learningPathItems.stream().filter(item -> item.getNextItem() == null)
                .findFirst().orElse(null);
    }

}
