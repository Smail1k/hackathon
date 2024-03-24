package ru.oleg.hackathon.domain.user.dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.oleg.hackathon.data.models.User;
import ru.oleg.hackathon.domain.course.dto.CourseOut;
import ru.oleg.hackathon.domain.course.dto.SimpleCourseOut;
import ru.oleg.hackathon.domain.task.dto.TaskOut;

import java.util.List;

@Component
@AllArgsConstructor
public class UserMapper {
    public UserOut mapToUserOut(User user, List<SimpleCourseOut> courses, List<TaskOut> tasks) {

        return new UserOut(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                courses,
                tasks
        );

    }

    public SimpleUserOut mapToSimpleUserOut(User user) {

        return new SimpleUserOut(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );

    }
}
