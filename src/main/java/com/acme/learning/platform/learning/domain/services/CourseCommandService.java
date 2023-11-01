package com.acme.learning.platform.learning.domain.services;

import com.acme.learning.platform.learning.domain.model.aggregates.Course;
import com.acme.learning.platform.learning.domain.model.commands.CreateCourseCommand;
import com.acme.learning.platform.learning.domain.model.commands.DeleteCourseCommand;
import com.acme.learning.platform.learning.domain.model.commands.UpdateCourseCommand;

import java.util.Optional;

/**
 * Course command service.
 */
public interface CourseCommandService {
    /**
     * Handle create course command.
     *
     * @param command the command
     * @return the long
     */
    Long handle(CreateCourseCommand command);

    /**
     * Handle update course command.
     *
     * @param command the command
     * @return the optional
     */
    Optional<Course> handle(UpdateCourseCommand command);

    /**
     * Handle delete course command.
     *
     * @param command the command
     */
    void handle(DeleteCourseCommand command);
}
