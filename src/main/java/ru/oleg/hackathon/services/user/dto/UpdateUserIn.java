package ru.oleg.hackathon.services.user.dto;

import jakarta.validation.constraints.Size;

public record UpdateUserIn(
        @Size(min = 1, max = 24)
        String username,
        @Size(min =1, max = 20)
        String firstName,

        @Size(min =1, max = 20)
        String lastName
) {
}
