package ru.oleg.hackathon.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 30)
    @Size(max = 30)
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @OneToMany(mappedBy = "team",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<UserTask> users = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

}
