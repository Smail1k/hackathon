package ru.oleg.hackathon.domain.auth.dto;

import jakarta.validation.constraints.*;

import java.io.Serializable;

public record RegisterIn(@NotNull @Size(max = 128) @Email @NotBlank String email,
                         @NotEmpty @NotNull @Size(min = 5, max = 15) String firstName,
                         @NotEmpty @NotNull @Size(min = 5, max = 15) String lastName,
                         @Size(max = 32) String username,
                         @NotNull @Size(max = 128) @NotBlank String password) implements Serializable {
}
