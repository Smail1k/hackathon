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
    @Size(max = 300)
    private String description;

    // TODO изменить CascadeType, иначе delete course = delete all members
    @OneToMany(mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<UserCourse> members;

    @OneToMany(mappedBy = "course")
    private Set<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;
}
