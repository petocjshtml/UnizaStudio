package org.example.unizastudio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminFrontendController {

    @GetMapping("/admin-technics")
    public String adminInterface() {
        return "admin/admin_technics";
    }

    @GetMapping("/admin-users")
    public String adminUsers() {
        return "admin/admin_users";
    }

    @GetMapping("/admin-borrowed")
    public String adminBorrowed() {
        return "admin/admin_borrowed";
    }

    @GetMapping("/admin-profile")
    public String adminProfile() {
        return "admin/admin_profile";
    }
}

