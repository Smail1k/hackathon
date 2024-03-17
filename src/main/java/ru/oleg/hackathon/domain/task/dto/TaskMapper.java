package ru.oleg.hackathon.domain.task.dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.oleg.hackathon.data.models.Task;

@AllArgsConstructor
@Component
public record TaskMapper() {
    public TaskOut mapToTaskOut(Task task) {

        return new TaskOut(
                task.getId(),
                task.getName(),
                task.getDescription());

    }
}
