package com.example.music_app.service.impl;

import com.example.music_app.entity.User;
import com.example.music_app.repository.UserRepository;
import com.example.music_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password));
    }

    @Override
    public Optional<User> getProfile(Long userId) {
        return userRepository.findById(userId);
    }
}

