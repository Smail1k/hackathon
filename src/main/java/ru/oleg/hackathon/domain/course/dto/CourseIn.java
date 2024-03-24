package ru.oleg.hackathon.domain.course.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseIn(@NotNull @Size(min = 2, max = 15) String name,
                       @Size(max = 300) String description) {
}
