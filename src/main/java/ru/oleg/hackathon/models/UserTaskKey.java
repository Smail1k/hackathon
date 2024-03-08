package ru.oleg.hackathon.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class UserTaskKey implements Serializable {

    @Column(name = "user_id")
    long userId;

    @Column(name = "task_id")
    long taskId;
}
