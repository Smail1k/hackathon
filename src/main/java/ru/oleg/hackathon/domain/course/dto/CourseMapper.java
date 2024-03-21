package ru.oleg.hackathon.domain.course.dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.oleg.hackathon.data.models.Course;

@Component
@AllArgsConstructor
public class CourseMapper {
    public CourseOut mapToCourseOut(Course course) {

        return new CourseOut(
                course.getId(),
                course.getName(),
                course.getDescription());
    }
}
