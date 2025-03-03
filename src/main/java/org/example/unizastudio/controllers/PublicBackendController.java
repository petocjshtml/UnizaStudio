package org.example.unizastudio.controllers;

import org.example.unizastudio.models.User;
import org.example.unizastudio.services.UserService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class PublicBackendController {

    private final UserService userService;

    public PublicBackendController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/public/register")
    public RedirectView processRegistration(@ModelAttribute User user) {
        boolean registered = userService.registerNewUser(user);
        if (registered) {
            return new RedirectView("/login");
        } else {
            // Tu môžeš pridať logiku pre odoslanie chybového hlásenia
            return new RedirectView("/register?error=RegistrationFailed");
        }
    }

    // Ostatné endpointy...
}

