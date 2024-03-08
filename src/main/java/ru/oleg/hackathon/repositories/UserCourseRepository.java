package ru.oleg.hackathon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.oleg.hackathon.models.UserCourse;
import ru.oleg.hackathon.models.UserCourseKey;

public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourseKey> {

}
