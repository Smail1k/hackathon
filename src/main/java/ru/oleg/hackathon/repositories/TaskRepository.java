package ru.oleg.hackathon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.oleg.hackathon.models.Task;
import ru.oleg.hackathon.models.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
