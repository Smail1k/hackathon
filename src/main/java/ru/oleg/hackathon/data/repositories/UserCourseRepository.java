package ru.oleg.hackathon.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.oleg.hackathon.data.models.UserCourse;
import ru.oleg.hackathon.data.models.UserCourseKey;

import java.util.List;
import java.util.Optional;

public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourseKey> {
    Optional<List<UserCourse>> findUserCoursesByUserId(long userId);
}
