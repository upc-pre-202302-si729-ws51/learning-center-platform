package com.acme.learning.platform.learning.interfaces.rest;

import com.acme.learning.platform.learning.domain.model.queries.GetStudentByAcmeStudentRecordIdQuery;
import com.acme.learning.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import com.acme.learning.platform.learning.domain.services.StudentCommandService;
import com.acme.learning.platform.learning.domain.services.StudentQueryService;
import com.acme.learning.platform.learning.interfaces.rest.resources.CreateStudentResource;
import com.acme.learning.platform.learning.interfaces.rest.resources.StudentResource;
import com.acme.learning.platform.learning.interfaces.rest.transform.CreateStudentCommandFromResourceAssembler;
import com.acme.learning.platform.learning.interfaces.rest.transform.StudentResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentsController {
    private final StudentCommandService studentCommandService;
    private final StudentQueryService studentQueryService;

    public StudentsController(StudentCommandService studentCommandService, StudentQueryService studentQueryService) {
        this.studentCommandService = studentCommandService;
        this.studentQueryService = studentQueryService;
    }

    /**
     * Creates a new student
     * @param resource the resource with the student data
     * @return the student resource
     */
    @PostMapping
    public ResponseEntity<StudentResource> createStudent(@RequestBody CreateStudentResource resource) {
        var createStudentCommand = CreateStudentCommandFromResourceAssembler.toCommandFromResource(resource);
        var studentId = studentCommandService.handle(createStudentCommand);
        if (studentId.studentRecordId().isEmpty()) return ResponseEntity.badRequest().build();
        var getStudentByAcmeStudentRecordIdQuery = new GetStudentByAcmeStudentRecordIdQuery(studentId);
        var student = studentQueryService.handle(getStudentByAcmeStudentRecordIdQuery);
        if (student.isEmpty()) return ResponseEntity.badRequest().build();
        var studentResource = StudentResourceFromEntityAssembler.toResourceFromEntity(student.get());
        return new ResponseEntity<>(studentResource, HttpStatus.CREATED);
    }

    /**
     * Gets a student by its acme student record id
     * @param studentRecordId the acme student record id
     * @return the student resource for the given record id
     */
    @GetMapping("/{studentRecordId}")
    public ResponseEntity<StudentResource> getStudentByAcmeStudentRecordId(@PathVariable String studentRecordId) {
        var acmeStudentRecordId = new AcmeStudentRecordId(studentRecordId);
        var getStudentByAcmeStudentRecordIdQuery = new GetStudentByAcmeStudentRecordIdQuery(acmeStudentRecordId);
        var student = studentQueryService.handle(getStudentByAcmeStudentRecordIdQuery);
        if (student.isEmpty()) return ResponseEntity.notFound().build();
        var studentResource = StudentResourceFromEntityAssembler.toResourceFromEntity(student.get());
        return ResponseEntity.ok(studentResource);
    }
}
