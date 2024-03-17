package ru.oleg.hackathon.domain.user.dto;

import ru.oleg.hackathon.domain.course.dto.CourseOut;
import ru.oleg.hackathon.domain.task.dto.TaskOut;

import java.util.List;

public record UserOut(long id,
                      String username,
                      String email,
                      String first_name,
                      String last_name,
                      List<CourseOut> courses,
                      List<TaskOut> tasks

                      ) {
}
