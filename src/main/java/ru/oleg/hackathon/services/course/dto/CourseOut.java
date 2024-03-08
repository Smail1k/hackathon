package ru.oleg.hackathon.services.course.dto;

import org.springframework.stereotype.Component;
import ru.oleg.hackathon.services.task.dto.TaskOut;
import ru.oleg.hackathon.services.user.dto.UserOut;

import java.util.List;

public record CourseOut(long id,
                        String name,
                        List<UserOut> members,
                        List<TaskOut> tasks

                        ) {
}
