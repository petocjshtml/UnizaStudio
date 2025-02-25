package com.example.UnizaStudio.controllers;
import com.example.UnizaStudio.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        String username = authentication.getName();
        return userService.getUserByUsername(username)
                .map(user -> ResponseEntity.ok(Map.of("username", user.getUsername(), "role", user.getRole())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/hello")
    public ResponseEntity<String> userHello() {
        return ResponseEntity.ok("Hello from authenticated user API!");
    }
}
