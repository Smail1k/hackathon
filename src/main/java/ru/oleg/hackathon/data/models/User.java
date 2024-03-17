package ru.oleg.hackathon.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import ru.oleg.hackathon.domain.auth.dto.Role;

import java.util.Set;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 24)
    @Size(max = 24)
    private String username;

    @Column(unique = true, length = 128, nullable = false)
    @Email
    @Size(max = 128)
    @NotNull
    @NotBlank
    private String email;

    @Column(length = 128, nullable = false)
    @Size(max = 128)
    @NotNull
    @NotBlank
    private String password;

    @Column(length = 20)
    @Size(max = 20)
    private String firstName;

    @Column(length = 20)
    @Size(max = 20)
    private String lastName;

    @Column(length = 10)
    @NotEmpty
    @NotBlank
    private Set<Role> role;

    @OneToMany(mappedBy = "user")
    private Set<UserCourse> courses;

    @OneToMany(mappedBy = "user")
    private Set<UserTask> tasks;

    @OneToMany(mappedBy = "author")
    private Set<Task> authorTasks;
}
