package ru.oleg.hackathon.domain.auth;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.oleg.hackathon.data.models.User;
import ru.oleg.hackathon.data.repositories.AuthRepository;
import ru.oleg.hackathon.data.repositories.UserRepository;
import ru.oleg.hackathon.domain.auth.dto.*;
import ru.oleg.hackathon.domain.exception.NotFoundException;
import ru.oleg.hackathon.domain.secutrity.JwtAuthentication;
import ru.oleg.hackathon.domain.secutrity.JwtProvider;
import ru.oleg.hackathon.domain.user.UserService;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final UserService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();

    public boolean checkEmail(final CheckEmailIn checkEmailIn) {
        return authRepository.existsByEmail(checkEmailIn.value());
    }

    public boolean checkUsername(final CheckUsernameIn checkUsernameIn) {
        return authRepository.existsByUsername(checkUsernameIn.value());
    }

    public JwtResponse login(@NonNull JwtRequest authRequest){
        final User user = userService.findUserByEmail(authRequest.getLogin())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (user.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getEmail(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new RuntimeException("Не правильный пароль");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.findUserByEmail(login)
                        .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.findUserByEmail(login)
                        .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getEmail(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    @Transactional
    public void register(final RegisterIn registerIn) {
        final User user = new User();
        user.setEmail(registerIn.email());
        if (registerIn.username() != null) {
            user.setUsername(registerIn.username());
        }
        user.setPassword(passwordEncoder.encode(registerIn.password()));

        userRepository.save(user);
    }
}
