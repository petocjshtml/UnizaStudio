package org.example.unizastudio.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminBackendController {
    @GetMapping("/admin/data")
    public String adminData() {
        return "Administrátorské dáta";
    }
}
