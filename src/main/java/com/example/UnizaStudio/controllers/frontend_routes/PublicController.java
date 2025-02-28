package com.example.UnizaStudio.controllers.frontend_routes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/")
@Controller
public class PublicController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("content", "components/home");
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("content", "components/about");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("content", "components/login");
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("content", "components/register");
        return "index";
    }
}
