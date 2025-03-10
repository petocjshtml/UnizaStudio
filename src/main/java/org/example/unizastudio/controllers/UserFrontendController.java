package org.example.unizastudio.controllers;
import org.example.unizastudio.models.User;
import org.example.unizastudio.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserFrontendController {

    private final UserService userService;

    public UserFrontendController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home() {
        return "user/home";
    }

    @GetMapping("/av-technics")
    public String avTechnics() {
        return "user/av_technics";
    }

    @GetMapping("/my-technics")
    public String myTechnics() {
        return "user/my_technics";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User user = userService.getCurrentUserSafe();
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "user/profile";
    }
}

