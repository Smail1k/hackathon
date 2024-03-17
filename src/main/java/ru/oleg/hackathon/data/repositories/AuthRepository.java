package ru.oleg.hackathon.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.oleg.hackathon.data.models.User;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
