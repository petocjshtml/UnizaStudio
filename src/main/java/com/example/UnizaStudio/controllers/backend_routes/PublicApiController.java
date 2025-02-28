package com.example.UnizaStudio.controllers.backend_routes;

import com.example.UnizaStudio.models.data_transfer_objects.RegisterDTO;
import com.example.UnizaStudio.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicApiController {

    private final UserService userService;

    public PublicApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody @Valid RegisterDTO registerDTO) {
        String result = userService.registerUser(registerDTO);
        Map<String, String> responseMap = new HashMap<>();

        if (!"OK".equals(result)) {
            responseMap.put("message", result);
            return ResponseEntity.badRequest().body(responseMap);
        }

        responseMap.put("message", "Registrácia úspešná! Teraz sa môžete prihlásiť.");
        return ResponseEntity.ok(responseMap);
    }
}
