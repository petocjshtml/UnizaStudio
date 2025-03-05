package org.example.unizastudio.controllers;

import org.example.unizastudio.models.User;
import org.example.unizastudio.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserBackendController {

    private final UserService userService;

    public UserBackendController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        User currentUser = userService.getCurrentUserSafe();
        return ResponseEntity.ok(currentUser);
    }
}
