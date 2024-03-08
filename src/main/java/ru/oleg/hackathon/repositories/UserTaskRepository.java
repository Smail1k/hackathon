package ru.oleg.hackathon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.oleg.hackathon.models.UserTask;
import ru.oleg.hackathon.models.UserTaskKey;

public interface UserTaskRepository extends JpaRepository<UserTask, UserTaskKey> {
}
