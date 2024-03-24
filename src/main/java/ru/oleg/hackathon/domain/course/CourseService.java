package ru.oleg.hackathon.domain.course;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.oleg.hackathon.data.models.*;
import ru.oleg.hackathon.data.repositories.CourseRepository;
import ru.oleg.hackathon.data.repositories.UserCourseRepository;
import ru.oleg.hackathon.data.repositories.UserRepository;
import ru.oleg.hackathon.domain.auth.dto.Role;
import ru.oleg.hackathon.domain.course.dto.*;
import ru.oleg.hackathon.domain.exception.ForbiddenException;
import ru.oleg.hackathon.domain.exception.NotFoundException;
import ru.oleg.hackathon.domain.secutrity.JwtAuthentication;
import ru.oleg.hackathon.domain.task.dto.TaskOut;
import ru.oleg.hackathon.domain.task.dto.UpdateTaskIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;

    public List<SimpleCourseOut> findAllCourse(final @NotNull Authentication authentication) {
        final User user = userRepository.findById(((JwtAuthentication) authentication).getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (user.getRole() == Role.ADMIN) {
            final List<Course> courses = courseRepository.findAll();
            return courses.stream().map(courseMapper::mapToSimpleCourseOut).toList();
        }

        throw new ForbiddenException("Недостаточно прав");
    }

    public List<SimpleCourseOut> findCoursesOfUser(final long userId) {
        final Optional<List<UserCourse>> userCourses =
                Optional.ofNullable(userCourseRepository.findUserCoursesByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Курсы не найдены")));

        if (userCourses.isPresent()) {
            List<Course> courses = userCourses.get().stream().map(UserCourse::getCourse).toList();
            return courses.stream().map(courseMapper::mapToSimpleCourseOut).toList();
        }

        return null;
    }

    public CourseOut findCourseById(final @NotNull Authentication authentication, final long courseId) {
        final User user = userRepository.findById(((JwtAuthentication) authentication).getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        final Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Курс не найден"));

        UserCourseKey userCourseKey = new UserCourseKey(user.getId(), course.getId());

        userCourseRepository.findById(userCourseKey)
                .orElseThrow(() -> new NotFoundException("Пользователь не связан с данным курсом"));

        final List<User> users = course.getMembers().stream().map(UserCourse::getUser).toList();

        return courseMapper.mapToCourseOut(course, users);
    }

    public CourseOut addCourse(final @NotNull Authentication authentication, final CourseIn courseIn) {
        final User user = userRepository.findById(((JwtAuthentication) authentication).getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (user.getRole() == Role.STUDENT) {
            throw new ForbiddenException("Недостаточно прав");
        }

        final Course course = new Course();
        course.setName(courseIn.name());
        course.setDescription(courseIn.description());
        course.setAuthor(user);

        courseRepository.save(course);
        return courseMapper.mapToCourseOut(course);
    }

    public CourseOut updateCourse(final @NotNull Authentication authentication,
                              final UpdateCourseIn updateCourseIn) {
        final long userId = ((JwtAuthentication) authentication).getUserId();

        Course course = courseRepository.findById(updateCourseIn.id())
                .orElseThrow(() -> new NotFoundException("Задача не найдена"));

        if (userId != course.getAuthor().getId()){
            throw new ForbiddenException("Недостаточно прав");
        }

        course.setName(updateCourseIn.name());
        course.setDescription(updateCourseIn.description());

        final List<User> users = course.getMembers().stream().map(UserCourse::getUser).toList();

        return courseMapper.mapToCourseOut(courseRepository.save(course), users);
    }

    public void deleteCourse(final @NotNull Authentication authentication,
                           final long courseId) {
        final long userId = ((JwtAuthentication) authentication).getUserId();

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Курс не найден"));

        if (userId != course.getAuthor().getId()){
            throw new ForbiddenException("Недостаточно прав");
        }

        courseRepository.delete(course);
    }
}
