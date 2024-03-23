package ru.oleg.hackathon.controllers.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.hackathon.domain.auth.AuthService;
import ru.oleg.hackathon.domain.auth.dto.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name = "1. Авторизация")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(final @RequestBody CheckEmailIn checkEmailIn) {
        return new ResponseEntity<>(null, authService.checkEmail(checkEmailIn) ?
                HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/checkUsername")
    public ResponseEntity<Boolean> checkUsername(final @RequestBody CheckUsernameIn checkUsernameIn) {
        return new ResponseEntity<>(authService.checkUsername(checkUsernameIn) ?
                HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest){
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(final @RequestBody RegisterIn registerIn) {
        authService.register(registerIn);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(
            @RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

}
