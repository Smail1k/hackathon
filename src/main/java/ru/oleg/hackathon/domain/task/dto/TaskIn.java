package ru.oleg.hackathon.domain.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskIn(
        @NotNull(message = "Название задачи не может быть пустым")
        @NotBlank(message = "Название задачи не может быть пустым")
        @Size(min = 1, max = 30)
        String name,
        @Size(max = 300)
        String description,
        @NotNull
        long courseId) {
}
