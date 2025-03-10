package org.example.unizastudio.controllers;

import org.example.unizastudio.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/admin")
public class AdminBackendController {
    private final UserService userService;

    public AdminBackendController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/edit-user", consumes = "multipart/form-data")
    public ResponseEntity<String> editUser(@RequestParam Long id,
                                           @RequestParam String email,
                                           @RequestParam String nickname,
                                           @RequestParam(required = false) String password,
                                           @RequestParam boolean isAdmin,
                                           @RequestParam(required = false) MultipartFile photo) {
        try {
            boolean result = userService.editUser(id, email, nickname, photo, isAdmin, password);
            if (!result) {
                return ResponseEntity.badRequest().body("Chyba: Nepodarilo sa upraviť používateľa.");
            }
            return ResponseEntity.ok("Používateľ úspešne aktualizovaný!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Chyba: " + e.getMessage());
        }
    }

    @GetMapping("/delete-user")
    public RedirectView deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return new RedirectView("/admin-users");
    }
}
