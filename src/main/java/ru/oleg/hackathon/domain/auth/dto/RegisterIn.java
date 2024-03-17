package ru.oleg.hackathon.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record RegisterIn(@NotNull @Size(max = 128) @Email @NotBlank String email,
                         @Size(max = 32) String username,
                         @NotNull @Size(max = 128) @NotBlank String password) implements Serializable {
}
