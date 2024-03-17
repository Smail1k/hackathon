package ru.oleg.hackathon.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.oleg.hackathon.data.models.UserTask;
import ru.oleg.hackathon.data.models.UserTaskKey;

import java.util.List;
import java.util.Optional;

public interface UserTaskRepository extends JpaRepository<UserTask, UserTaskKey> {
    Optional<List<UserTask>> findUserTasksByUserId(long userId);
}
