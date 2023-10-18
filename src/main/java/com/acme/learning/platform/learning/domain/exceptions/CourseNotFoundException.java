package com.acme.learning.platform.learning.domain.exceptions;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long courseId) {
        super("Course with id " + courseId + " not found");
    }
}
