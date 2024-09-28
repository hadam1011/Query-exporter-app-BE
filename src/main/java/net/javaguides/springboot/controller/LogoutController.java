package net.javaguides.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Perform logout logic here, if any

        // Example: simply return a success message
        return ResponseEntity.ok("Logout successful");
    }
}

