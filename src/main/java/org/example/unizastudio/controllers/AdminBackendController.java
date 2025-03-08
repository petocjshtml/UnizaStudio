package org.example.unizastudio.controllers;

import org.example.unizastudio.services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminBackendController {
    private final UserService userService;

    public AdminBackendController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/edit-user")
    public RedirectView editUser(@RequestBody Map<String, String> formData) {
        Long id = Long.parseLong(formData.get("id"));
        String email = formData.get("email");
        String nickname = formData.get("nickname");
        boolean isAdmin = Boolean.parseBoolean(formData.get("isAdmin"));
        String password = formData.get("password"); // Môže byť prázdne
        userService.editUser(id, email, nickname, isAdmin, password);
        return new RedirectView("/admin-users");
    }

    @GetMapping("/delete-user")
    public RedirectView deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return new RedirectView("/admin-users");
    }
}
