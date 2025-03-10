package org.example.unizastudio.controllers;

import org.example.unizastudio.models.User;
import org.example.unizastudio.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // Endpoint pre editáciu profilu
    @PostMapping("/edit-profile")
    public ResponseEntity<String> editProfile(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) MultipartFile photo) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserByEmail(auth.getName());

        if (currentUser == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        boolean success = userService.editProfile(currentUser.getId(), email, nickname, photo);

        if (!success) {
            return ResponseEntity.badRequest().body("Email already in use or invalid data provided.");
        }

        return ResponseEntity.ok("Profile updated successfully.");
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserByEmail(auth.getName());

        if (currentUser == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        boolean passwordMatches = userService.checkPassword(currentUser.getId(), currentPassword);
        if (!passwordMatches) {
            return ResponseEntity.badRequest().body("Incorrect current password.");
        }

        userService.updatePassword(currentUser.getId(), newPassword);
        return ResponseEntity.ok("Password changed successfully.");
    }
}
