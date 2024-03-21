package ru.oleg.hackathon.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20)
    @Size(max = 20)
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<UserCourse> members;

    @OneToMany(mappedBy = "course")
    private Set<Task> tasks;
}
