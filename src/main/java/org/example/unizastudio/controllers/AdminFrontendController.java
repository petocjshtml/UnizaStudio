package org.example.unizastudio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminFrontendController {

    @GetMapping("/object-management")
    public String objectManagement() {
        return "object-management";
    }
}

