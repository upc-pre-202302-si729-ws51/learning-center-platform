package com.acme.learning.platform.learning.domain.model.entities;

import com.acme.learning.platform.learning.domain.model.aggregates.Enrollment;
import com.acme.learning.platform.learning.domain.model.valueobjects.ProgressStatus;
import com.acme.learning.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class ProgressRecordItem extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @Getter
    @ManyToOne
    @JoinColumn(name = "tutorial_id")
    private Tutorial tutorial;

    private ProgressStatus status;

    private Date startedAt;

    private Date completedAt;

    public ProgressRecordItem() {
    }

    public ProgressRecordItem(Enrollment enrollment, Tutorial tutorial) {
        this.enrollment = enrollment;
        this.tutorial = tutorial;
        this.status = ProgressStatus.NOT_STARTED;
    }

    public void start() {
        this.status = ProgressStatus.STARTED;
        this.startedAt = new Date();
    }

    public void complete() {
        this.status = ProgressStatus.COMPLETED;
        this.completedAt = new Date();
    }

    public boolean isInProgress() {
        return this.status == ProgressStatus.STARTED;
    }

    public boolean isCompleted() {
        return this.status == ProgressStatus.COMPLETED;
    }

    public boolean isNotStarted() {
        return this.status == ProgressStatus.NOT_STARTED;
    }

    /**
     * Calculates the days elapsed since the item was started.
     *
     * @return zero if the item is not started. Otherwise, it returns the number of days elapsed between the started date and the completed date (if completed) or today.
     */
    public long calculateDaysElapsed() {

        if (this.isNotStarted()) {
            return 0;
        }

        var defaultTimeZone = java.time.ZoneId.systemDefault();
        // Only started items are registered, so it should be the started date
        var fromDate = this.startedAt.toInstant();
        // If completed it should be the completed date, otherwise it should be today
        var toDate = this.isCompleted() ? this.completedAt.toInstant() : LocalDate.now().atStartOfDay(defaultTimeZone).toInstant();

        return java.time.Duration.between(fromDate, toDate).toDays();

    }
}
