package com.acme.learning.platform.learning.domain.model.commands;

public record UpdateCourseCommand(Long id, String title, String description) {
}
