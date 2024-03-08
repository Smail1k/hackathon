package ru.oleg.hackathon.services.course;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.hackathon.repositories.CourseRepository;
import ru.oleg.hackathon.services.course.dto.CourseMapper;
import ru.oleg.hackathon.services.course.dto.CourseOut;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
}
