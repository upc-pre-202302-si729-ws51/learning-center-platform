package com.acme.learning.platform.learning.infrastructure.repositories;

import com.acme.learning.platform.learning.domain.model.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
