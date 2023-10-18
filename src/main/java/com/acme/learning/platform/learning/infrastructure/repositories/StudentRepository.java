package com.acme.learning.platform.learning.infrastructure.repositories;

import com.acme.learning.platform.learning.domain.model.aggregates.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentRecordId(String studentRecordId);
}
