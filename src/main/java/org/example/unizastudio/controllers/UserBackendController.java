package org.example.unizastudio.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserBackendController {

    @GetMapping("/user/data")
    public String userData() {
        return "Používateľské dáta";
    }
}
