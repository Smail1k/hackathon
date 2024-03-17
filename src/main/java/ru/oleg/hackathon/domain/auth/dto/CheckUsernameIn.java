package ru.oleg.hackathon.domain.auth.dto;

import jakarta.validation.constraints.NotNull;

public record CheckUsernameIn(@NotNull String value) {
}
