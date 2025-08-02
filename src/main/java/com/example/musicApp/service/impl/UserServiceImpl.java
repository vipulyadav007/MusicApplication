package com.example.musicApp.service.impl;

import com.example.musicApp.entity.User;
import com.example.musicApp.repository.UserRepository;
import com.example.musicApp.service.UserService;
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

