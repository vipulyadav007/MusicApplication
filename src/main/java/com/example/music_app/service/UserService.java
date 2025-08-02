package com.example.music_app.service;

import com.example.music_app.entity.User;
import java.util.Optional;

public interface UserService {
    User register(User user);
    Optional<User> login(String username, String password);
    Optional<User> getProfile(Long userId);
}

