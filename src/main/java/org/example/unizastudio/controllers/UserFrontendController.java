package org.example.unizastudio.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserFrontendController {

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
    public String profile() {
        return "user/profile";
    }
}

