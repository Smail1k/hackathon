package ru.oleg.hackathon.services.user.dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.oleg.hackathon.models.User;
import ru.oleg.hackathon.services.user.dto.UserOut;

@Component
@AllArgsConstructor
public class UserMapper {
    public UserOut mapToUserOut(User user) {

        return new UserOut(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName());

    }
}
