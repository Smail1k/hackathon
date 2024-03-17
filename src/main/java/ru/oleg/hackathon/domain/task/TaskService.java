package ru.oleg.hackathon.domain.task;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.oleg.hackathon.data.models.*;
import ru.oleg.hackathon.data.repositories.*;
import ru.oleg.hackathon.domain.secutrity.JwtAuthentication;
import ru.oleg.hackathon.domain.exception.ForbiddenException;
import ru.oleg.hackathon.domain.exception.NotFoundException;
import ru.oleg.hackathon.domain.task.dto.TaskIn;
import ru.oleg.hackathon.domain.task.dto.TaskMapper;
import ru.oleg.hackathon.domain.task.dto.TaskOut;
import ru.oleg.hackathon.domain.task.dto.UpdateTaskIn;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;
    private final UserTaskRepository userTaskRepository;

    public TaskOut findTaskById(final long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        return taskMapper.mapToTaskOut(task);
    }

    public List<TaskOut> findTasksOfUser(final long userId) {
        final Optional<List<UserTask>> userTasks =
                Optional.ofNullable(userTaskRepository.findUserTasksByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Задачи не найдены")));

        if (userTasks.isPresent()) {
            List<Task> tasks = userTasks.get().stream().map(UserTask::getTask).toList();
            return tasks.stream().map(taskMapper::mapToTaskOut).toList();
        }
        return null;
    }

    public TaskOut addTask(final @NotNull Authentication authentication,
                           final TaskIn taskIn){
        // TODO двойная свзяь и авторство юзера
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

        taskMapper.mapToTaskOut(taskRepository.save(task));

        return null;
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
                           final long taskId) {
        final long userId = ((JwtAuthentication) authentication).getUserId();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Задача не найдена"));

        if (userId != task.getAuthor().getId()){
            throw new ForbiddenException("Недостаточно прав");
        }

        taskRepository.delete(task);
    }
}
