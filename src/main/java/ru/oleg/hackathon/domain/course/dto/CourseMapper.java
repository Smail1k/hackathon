package ru.oleg.hackathon.domain.course.dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.oleg.hackathon.data.models.Course;
import ru.oleg.hackathon.data.models.Task;
import ru.oleg.hackathon.data.models.User;
import ru.oleg.hackathon.domain.task.dto.TaskMapper;
import ru.oleg.hackathon.domain.task.dto.TaskOut;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CourseMapper {
    private final TaskMapper taskMapper;
    @SafeVarargs
    public final CourseOut mapToCourseOut(Course course, List<User>... users) {
        List<Task> tasks = new ArrayList<>(course.getTasks());

        return new CourseOut(
                course.getId(),
                course.getName(),
                course.getDescription(),
                users,
                tasks);
    }

    public SimpleCourseOut mapToSimpleCourseOut(Course course) {

        return new SimpleCourseOut(
                course.getId(),
                course.getName(),
                course.getDescription());
    }
}
