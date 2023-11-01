package com.acme.learning.platform.learning.application.internal.commandservices;

import com.acme.learning.platform.learning.domain.model.aggregates.Course;
import com.acme.learning.platform.learning.domain.model.commands.CreateCourseCommand;
import com.acme.learning.platform.learning.domain.model.commands.DeleteCourseCommand;
import com.acme.learning.platform.learning.domain.model.commands.UpdateCourseCommand;
import com.acme.learning.platform.learning.domain.services.CourseCommandService;
import com.acme.learning.platform.learning.infrastructure.persistence.jpa.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link CourseCommandService} interface.
 */
@Service
public class CourseCommandServiceImpl implements CourseCommandService {
    private final CourseRepository courseRepository;

    public CourseCommandServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long handle(CreateCourseCommand command) {
        if (courseRepository.existsByTitle(command.title()))
            throw new IllegalArgumentException("Course with same title already exists");
        var course = new Course(command.title(), command.description());
        courseRepository.save(course);
        return course.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Course> handle(UpdateCourseCommand command) {
        if (!courseRepository.existsById(command.id())) throw new IllegalArgumentException("Course does not exists");
        var courseToUpdate = courseRepository.findById(command.id()).get();
        if (courseRepository.existsByTitleAndIdIsNot(command.title(), command.id()))
            throw new IllegalArgumentException("Course with same title already exists");
        var updatedCourse = courseRepository.save(courseToUpdate.updateInformation(command.title(), command.description()));
        return Optional.of(updatedCourse);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(DeleteCourseCommand command) {
        if (!courseRepository.existsById(command.courseId()))
            throw new IllegalArgumentException("Course does not exists");
        courseRepository.deleteById(command.courseId());

    }
}
