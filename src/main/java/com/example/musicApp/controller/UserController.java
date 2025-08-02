package com.example.musicApp.controller;

import com.example.musicApp.entity.User;
import com.example.musicApp.security.SecurityManager;
import com.example.musicApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        User currentUser = SecurityManager.getCurrentUser();
        if (currentUser == null || !SecurityManager.isAdmin()) {
            return ResponseEntity.status(403).body("You are not Admin");
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty() ||
                user.getPassword() == null || user.getPassword().trim().isEmpty() ||
                user.getRole() == null || user.getRole().trim().isEmpty()) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty() ||
                user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return ResponseEntity.status(400).build();
        }
        Optional<User> found = userService.login(user.getUsername(), user.getPassword());
        if (found.isPresent()) {
            String token = UUID.randomUUID().toString();
            SecurityManager.tokenStore.put(token, found.get().getId());
            Map<String, Object> resp = new HashMap<>();
            resp.put("token", token);
            resp.put("user", found.get());
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Long id) {
        User currentUser = SecurityManager.getCurrentUser();
        if (currentUser == null || !SecurityManager.isAdmin()) {
            return ResponseEntity.status(403).body("You are not Admin");
        }
        return userService.getProfile(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
