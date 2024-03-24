package ru.oleg.hackathon.domain.course.dto;


import ru.oleg.hackathon.data.models.Task;
import ru.oleg.hackathon.data.models.User;

import java.util.List;

public record CourseOut(long id,
                        String name,
                        String description,
                        List<User>[] members,
                        List<Task> tasks
                        ) {
}
