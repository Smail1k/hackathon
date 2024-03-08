package ru.oleg.hackathon.services.task;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.hackathon.models.*;
import ru.oleg.hackathon.repositories.CourseRepository;
import ru.oleg.hackathon.repositories.TaskRepository;
import ru.oleg.hackathon.repositories.UserCourseRepository;
import ru.oleg.hackathon.repositories.UserRepository;
import ru.oleg.hackathon.services.exception.ForbiddenException;
import ru.oleg.hackathon.services.exception.NotFoundException;
import ru.oleg.hackathon.services.task.dto.TaskIn;
import ru.oleg.hackathon.services.task.dto.TaskMapper;
import ru.oleg.hackathon.services.task.dto.TaskOut;
import ru.oleg.hackathon.services.task.dto.UpdateTaskIn;
import ru.oleg.hackathon.services.user.dto.UpdateUserIn;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;

    public TaskOut findTaskById(long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        return taskMapper.mapToTaskOut(task);
    }

    public TaskOut addTask(final @NotNull Authentication authentication,
                           final TaskIn taskIn){
        final User user = userRepository.findById(((JwtAuthentication) authentication).getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        final Course course = courseRepository.findById(taskIn.courseId())
                .orElseThrow(() -> new NotFoundException("Курс не найден"));

        UserCourseKey userCourseKey = new UserCourseKey(user.getId(), course.getId());

        final UserCourse userCourse = userCourseRepository.findById(userCourseKey)
                .orElseThrow(() -> new NotFoundException("Связь курса с пользователем не найдена"));

        Task task = new Task();

        task.setName(taskIn.name());
        task.setDescription(taskIn.description());
        task.setCourse(course);
        task.setAuthor(user);

        return taskMapper.mapToTaskOut(taskRepository.save(task));
    }

    public TaskOut updateTask(final @NotNull Authentication authentication,
                              final UpdateTaskIn updateTaskIn) {
        final long userId = ((JwtAuthentication) authentication).getUserId();

        Task task = taskRepository.findById(updateTaskIn.id())
                .orElseThrow(() -> new NotFoundException("Задача не найдена"));

        if (userId != task.getAuthor().getId()){
            throw new ForbiddenException("Недостаточно прав");
        }

        task.setName(updateTaskIn.name());
        task.setDescription(updateTaskIn.description());

        return taskMapper.mapToTaskOut(taskRepository.save(task));
    }

    public void deleteTask(final @NotNull Authentication authentication,
                           long id) {
        final long userId = ((JwtAuthentication) authentication).getUserId();

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Задача не найдена"));

        if (userId != task.getAuthor().getId()){
            throw new ForbiddenException("Недостаточно прав");
        }

        taskRepository.delete(task);
    }
}
