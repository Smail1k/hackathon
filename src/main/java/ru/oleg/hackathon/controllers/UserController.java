package ru.oleg.hackathon.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.oleg.hackathon.services.user.dto.UserOut;
import ru.oleg.hackathon.services.user.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public ResponseEntity<UserOut> getUserById(final @PathVariable long userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }
}
