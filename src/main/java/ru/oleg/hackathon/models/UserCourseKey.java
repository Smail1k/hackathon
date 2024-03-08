package ru.oleg.hackathon.models;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class UserCourseKey implements Serializable {
    @Column(name = "user_id")
    long userId;

    @Column(name = "course_id")
    long courseId;

    public UserCourseKey(long userId, long courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }
}
