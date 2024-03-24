package ru.oleg.hackathon.controllers.course;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.oleg.hackathon.domain.course.CourseService;
import ru.oleg.hackathon.domain.course.dto.CourseIn;
import ru.oleg.hackathon.domain.course.dto.CourseOut;
import ru.oleg.hackathon.domain.course.dto.SimpleCourseOut;
import ru.oleg.hackathon.domain.course.dto.UpdateCourseIn;

import java.util.List;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
@Tag(name = "3. Курсы")

public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<SimpleCourseOut>> allCourse(final @NotNull Authentication authentication) {
        return ResponseEntity.ok(courseService.findAllCourse(authentication));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseOut> getCourseById(final @NotNull Authentication authentication,
                                                               final @PathVariable long courseId) {
        return ResponseEntity.ok(courseService.findCourseById(authentication, courseId));
    }

    @PostMapping()
    public ResponseEntity<CourseOut> addCourse(final @NotNull Authentication authentication,
                                               final @NotNull CourseIn courseIn){
        return ResponseEntity.ok(courseService.addCourse(authentication, courseIn));
    }

    @PatchMapping("/{courseId}/update")
    public ResponseEntity<CourseOut> updateCourse(final @NotNull Authentication authentication,
                                                  final @NotNull UpdateCourseIn updateCourseIn,
                                                  final @PathVariable long courseId){
        return ResponseEntity.ok(courseService.updateCourse(authentication, updateCourseIn));
    }

    @PatchMapping("/{courseId}/delete")
    public ResponseEntity<?> deleteCourse(final @NotNull Authentication authentication,
                                          final @PathVariable long courseId){
        courseService.deleteCourse(authentication, courseId);
        return ResponseEntity.ok().build();
    }
}
