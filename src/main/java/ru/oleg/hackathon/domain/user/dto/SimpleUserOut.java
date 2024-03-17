package ru.oleg.hackathon.domain.user.dto;

public record SimpleUserOut(long id,
                            String username,
                            String email,
                            String first_name,
                            String last_name) {
}
