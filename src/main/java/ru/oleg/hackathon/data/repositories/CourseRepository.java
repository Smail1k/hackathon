package ru.oleg.hackathon.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.oleg.hackathon.data.models.Course;

@Repository
public interface CourseRepository  extends JpaRepository<Course, Long> {

}
