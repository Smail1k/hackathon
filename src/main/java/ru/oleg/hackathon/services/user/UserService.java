package ru.oleg.hackathon.services.user;

import jakarta.security.auth.message.AuthException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import ru.oleg.hackathon.models.User;
import ru.oleg.hackathon.repositories.UserRepository;
import ru.oleg.hackathon.services.exception.NotFoundException;
import ru.oleg.hackathon.services.user.dto.UpdateUserIn;
import ru.oleg.hackathon.services.user.dto.UserMapper;
import ru.oleg.hackathon.services.user.dto.UserOut;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserOut> findAll(){
        final List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::mapToUserOut).toList();
    }


    public UserOut findUserById(final long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return userMapper.mapToUserOut(user);
    }


    public UserOut findUserByUsername(final String username) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return userMapper.mapToUserOut(user);
    }


    public User findUserByEmail(final String email){
        return userRepository.findByEmail(email);
    }

    public User updateUser(final @NotNull Authentication authentication,
                           final UpdateUserIn updateUserIn) {
        final User user = userRepository.findById(((JwtAuthentication) authentication).getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (updateUserIn.username() != null) {
            user.setUsername(updateUserIn.username());
        }

        user.setFirstName(updateUserIn.firstName());
        user.setLastName(updateUserIn.lastName());

        return userRepository.save(user);
    }

    public void deleteUser(final @NotNull Authentication authentication) {
        final User user = userRepository.findById(((JwtAuthentication) authentication).getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        userRepository.delete(user);
    }
}
