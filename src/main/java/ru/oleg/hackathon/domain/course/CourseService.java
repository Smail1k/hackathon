package ru.oleg.hackathon.domain.course;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.hackathon.data.models.Course;
import ru.oleg.hackathon.data.models.UserCourse;
import ru.oleg.hackathon.data.repositories.CourseRepository;
import ru.oleg.hackathon.data.repositories.UserCourseRepository;
import ru.oleg.hackathon.domain.course.dto.CourseMapper;
import ru.oleg.hackathon.domain.course.dto.CourseOut;
import ru.oleg.hackathon.domain.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;
    private final CourseMapper courseMapper;

    public List<CourseOut> findAllCourse() {
        final List<Course> courses = courseRepository.findAll();
        return courses.stream().map(courseMapper::mapToCourseOut).toList();
    }

    public List<CourseOut> findCoursesOfUser(final long userId) {
        final Optional<List<UserCourse>> userCourses =
                Optional.ofNullable(userCourseRepository.findUserCoursesByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Курсы не найдены")));

        if (userCourses.isPresent()) {
            List<Course> courses = userCourses.get().stream().map(UserCourse::getCourse).toList();
            return courses.stream().map(courseMapper::mapToCourseOut).toList();
        }
        return null;
    }
}
