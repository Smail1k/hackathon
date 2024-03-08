package ru.oleg.hackathon.services.user.dto;

public record UserOut(long id,
                      String username,
                      String email,
                      String first_name,
                      String last_name

                      ) {
}
