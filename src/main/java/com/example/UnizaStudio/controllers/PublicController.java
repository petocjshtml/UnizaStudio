package com.example.UnizaStudio.controllers;

import com.example.UnizaStudio.models.User;
import com.example.UnizaStudio.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final UserService userService;

    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");

            User user = userService.registerUser(username, password);
            return ResponseEntity.ok(Map.of("message", "User registered successfully", "id", user.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


    @GetMapping("/hello")
    public ResponseEntity<String> publicHello() {
        return ResponseEntity.ok("Hello from public API!");
    }
}
