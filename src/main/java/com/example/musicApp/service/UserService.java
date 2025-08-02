package com.example.musicApp.service;

import com.example.musicApp.entity.User;
import java.util.Optional;

public interface UserService {
    User register(User user);
    Optional<User> login(String username, String password);
    Optional<User> getProfile(Long userId);
}

