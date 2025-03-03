package org.example.unizastudio.controllers;
import org.example.unizastudio.services.UserService;
import org.springframework.ui.Model;
import org.example.unizastudio.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserFrontendController {
    private final UserService userService;

    public UserFrontendController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        User currentUser = userService.getCurrentUserSafe();
        model.addAttribute("user", currentUser);
        return "user/home";
    }

    @GetMapping("/av-technics")
    public String avTechnics(Model model) {
        User currentUser = userService.getCurrentUserSafe();
        model.addAttribute("user", currentUser);
        return "user/av_technics";
    }

    @GetMapping("/my-technics")
    public String myTechnics(Model model) {
        User currentUser = userService.getCurrentUserSafe();
        model.addAttribute("user", currentUser);
        return "user/my_technics";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User currentUser = userService.getCurrentUserSafe();
        model.addAttribute("user", currentUser);
        return "user/profile";
    }
}

