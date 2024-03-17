package ru.oleg.hackathon.domain.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import ru.oleg.hackathon.data.models.User;
import ru.oleg.hackathon.data.repositories.UserRepository;
import ru.oleg.hackathon.domain.course.CourseService;
import ru.oleg.hackathon.domain.course.dto.CourseOut;
import ru.oleg.hackathon.domain.exception.NotFoundException;
import ru.oleg.hackathon.domain.task.TaskService;
import ru.oleg.hackathon.domain.task.dto.TaskOut;
import ru.oleg.hackathon.domain.user.dto.SimpleUserOut;
import ru.oleg.hackathon.domain.user.dto.UpdateUserIn;
import ru.oleg.hackathon.domain.user.dto.UserMapper;
import ru.oleg.hackathon.domain.user.dto.UserOut;
import ru.oleg.hackathon.domain.secutrity.JwtAuthentication;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CourseService courseService;
    private final TaskService taskService;

    public List<SimpleUserOut> findAll(){
        final List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::mapToSimpleUserOut).toList();
    }


    public UserOut findUserById(final long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        final List<CourseOut> courseOuts = courseService.findCoursesOfUser(user.getId());
        final List<TaskOut> taskOuts = taskService.findTasksOfUser(user.getId());
        return userMapper.mapToUserOut(user, courseOuts, taskOuts);
    }


    public UserOut findMe(final @NotNull Authentication authentication) {
        final User user = userRepository.findById(((JwtAuthentication) authentication).getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        final List<CourseOut> courseOuts = courseService.findCoursesOfUser(user.getId());
        final List<TaskOut> taskOuts = taskService.findTasksOfUser(user.getId());
        return userMapper.mapToUserOut(user, courseOuts, taskOuts);
    }

    public UserOut findUserByUsername(final String username) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        final List<CourseOut> courseOuts = courseService.findCoursesOfUser(user.getId());
        final List<TaskOut> taskOuts = taskService.findTasksOfUser(user.getId());
        return userMapper.mapToUserOut(user, courseOuts, taskOuts);
    }


    public Optional<User> findUserByEmail(final String email){
        return userRepository.findByEmail(email);
    }

    public SimpleUserOut updateUser(final @NotNull Authentication authentication,
                                    final UpdateUserIn updateUserIn) {
        final User user = userRepository.findById(((JwtAuthentication) authentication).getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (updateUserIn.username() != null) {
            user.setUsername(updateUserIn.username());
        }

        user.setFirstName(updateUserIn.firstName());
        user.setLastName(updateUserIn.lastName());

        return userMapper.mapToSimpleUserOut(userRepository.save(user));
    }

    public void deleteUser(final @NotNull Authentication authentication) {
        final User user = userRepository.findById(((JwtAuthentication) authentication).getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        userRepository.delete(user);
    }
}
