package ru.oleg.hackathon.domain.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCourseIn(long id,
                             @Size(min = 1, max = 30)
                             @NotNull(message = "Название задачи не может быть пустым")
                             @NotBlank(message = "Название задачи не может быть пустым")
                             String name,

                             String description) {
}
