package org.example.unizastudio.controllers;

import org.example.unizastudio.models.User;
import org.example.unizastudio.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminFrontendController {

    private final UserService userService;

    public AdminFrontendController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin-technics")
    public String adminInterface() {
        return "admin/admin_technics";
    }

    @GetMapping("/admin-users")
    public String adminUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/admin_users";
    }

    @GetMapping("/admin-borrowed")
    public String adminBorrowed() {
        return "admin/admin_borrowed";
    }

    @GetMapping("/admin-profile")
    public String adminProfile(Model model) {
        User user = userService.getCurrentUserSafe();

        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "admin/admin_profile";
    }

}

