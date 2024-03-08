package ru.oleg.hackathon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.oleg.hackathon.models.Course;

@Repository
public interface CourseRepository  extends JpaRepository<Course, Long> {

}
