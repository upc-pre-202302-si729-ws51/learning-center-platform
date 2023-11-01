package com.acme.learning.platform.learning.interfaces.rest;

import com.acme.learning.platform.learning.domain.model.commands.DeleteCourseCommand;
import com.acme.learning.platform.learning.domain.model.queries.GetAllCoursesQuery;
import com.acme.learning.platform.learning.domain.model.queries.GetCourseByIdQuery;
import com.acme.learning.platform.learning.domain.services.CourseCommandService;
import com.acme.learning.platform.learning.domain.services.CourseQueryService;
import com.acme.learning.platform.learning.interfaces.rest.resources.CourseResource;
import com.acme.learning.platform.learning.interfaces.rest.resources.CreateCourseResource;
import com.acme.learning.platform.learning.interfaces.rest.resources.UpdateCourseResource;
import com.acme.learning.platform.learning.interfaces.rest.transform.CourseResourceFromEntityAssembler;
import com.acme.learning.platform.learning.interfaces.rest.transform.CreateCourseCommandFromResourceAssembler;
import com.acme.learning.platform.learning.interfaces.rest.transform.UpdateCourseCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * CoursesController is the REST controller for the Courses resource.
 * It exposes the following endpoints:
 * <ul>
 *     <li>POST /api/v1/courses</li>
 *     <li>GET /api/v1/courses/{courseId}</li>
 *     <li>GET /api/v1/courses</li>
 *     <li>PUT /api/v1/courses/{courseId}</li>
 *     <li>DELETE /api/v1/courses/{courseId}</li>
 * </ul>
 * <p>
 *     The controller uses the {@link CourseCommandService} and {@link CourseQueryService} to handle the requests.
 *
 */
@RestController
@RequestMapping(value = "/api/v1/courses", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Courses", description = "Courses Management Endpoints")
public class CoursesController {
    private final CourseCommandService courseCommandService;
    private final CourseQueryService courseQueryService;

    public CoursesController(CourseCommandService courseCommandService, CourseQueryService courseQueryService) {
        this.courseCommandService = courseCommandService;
        this.courseQueryService = courseQueryService;
    }

    /**
     * Creates a new course.
     * @param createCourseResource the resource containing the course data.
     * @return the created course resource.
     * @see CreateCourseResource
     * @see CourseResource
     */
    @PostMapping
    public ResponseEntity<CourseResource> createCourse(@RequestBody CreateCourseResource createCourseResource) {
        var createCourseCommand = CreateCourseCommandFromResourceAssembler.toCommandFromResource(createCourseResource);
        var courseId = courseCommandService.handle(createCourseCommand);
        if (courseId == 0L) return ResponseEntity.badRequest().build();
        var getCourseByIdQuery = new GetCourseByIdQuery(courseId);
        var course = courseQueryService.handle(getCourseByIdQuery);
        if (course.isEmpty()) return ResponseEntity.notFound().build();
        var courseResource = CourseResourceFromEntityAssembler.toResourceFromEntity(course.get());
        return new ResponseEntity<>(courseResource, HttpStatus.CREATED);
    }

    /**
     * Gets a course by its id.
     * @param courseId the course id.
     * @return the course resource.
     * @see CourseResource
     */
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResource> getCourseById(@PathVariable Long courseId) {
        var getCourseByIdQuery = new GetCourseByIdQuery(courseId);
        var course = courseQueryService.handle(getCourseByIdQuery);
        if (course.isEmpty()) return ResponseEntity.notFound().build();
        var courseResource = CourseResourceFromEntityAssembler.toResourceFromEntity(course.get());
        return ResponseEntity.ok(courseResource);
    }

    /**
     * Gets all courses.
     * @return the list of course resources.
     * @see CourseResource
     */
    @GetMapping
    public ResponseEntity<List<CourseResource>> getAllCourses() {
        var getAllCoursesQuery = new GetAllCoursesQuery();
        var courses = courseQueryService.handle(getAllCoursesQuery);
        var courseResources = courses.stream()
                .map(CourseResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(courseResources);
    }

    /**
     * Updates a course.
     * @param courseId the course id.
     * @param updateCourseResource the resource containing the course data.
     * @return the updated course resource.
     * @see UpdateCourseResource
     * @see CourseResource
     */
    @PutMapping("/{courseId}")
    public ResponseEntity<CourseResource> updateCourse(@PathVariable Long courseId, @RequestBody UpdateCourseResource updateCourseResource) {
        var updateCourseCommand = UpdateCourseCommandFromResourceAssembler.toCommandFromResource(courseId, updateCourseResource);
        var updatedCourse = courseCommandService.handle(updateCourseCommand);
        if (updatedCourse.isEmpty()) return ResponseEntity.notFound().build();
        var courseResource = CourseResourceFromEntityAssembler.toResourceFromEntity(updatedCourse.get());
        return ResponseEntity.ok(courseResource);
    }

    /**
     * Deletes a course.
     * @param courseId the id of the course to be deleted.
     * @return a response entity with Deletion confirmation message.
     */
    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        var deleteCourseCommand = new DeleteCourseCommand(courseId);
        courseCommandService.handle(deleteCourseCommand);
        return ResponseEntity.ok("Course with given id successfully deleted.");
    }
}
