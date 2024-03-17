package ru.oleg.hackathon.controllers.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.oleg.hackathon.domain.user.dto.SimpleUserOut;
import ru.oleg.hackathon.domain.user.dto.UpdateUserIn;
import ru.oleg.hackathon.domain.user.dto.UserOut;
import ru.oleg.hackathon.domain.user.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<SimpleUserOut>> getAllUser(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserOut> getUserById(final @PathVariable long userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<UserOut> me(final @NotNull Authentication authentication) {
        return ResponseEntity.ok(userService.findMe(authentication));
    }

    @GetMapping("{username}")
    public ResponseEntity<UserOut> getUserByUsername(final @PathVariable String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }

    @PatchMapping()
    public ResponseEntity<SimpleUserOut> updateUser(@NotNull Authentication authentication,
                                                    @RequestBody @Validated UpdateUserIn updateUserIn) {
        return ResponseEntity.ok(userService.updateUser(authentication, updateUserIn));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser(@NotNull Authentication authentication) {
        userService.deleteUser(authentication);
        return ResponseEntity.ok().build();
    }

}
