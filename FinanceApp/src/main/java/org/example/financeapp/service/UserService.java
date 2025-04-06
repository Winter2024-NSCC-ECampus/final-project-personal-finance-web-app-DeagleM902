package org.example.financeapp.service;

import jakarta.transaction.Transactional;
import org.example.financeapp.model.User;
import org.example.financeapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(User user) {
        System.out.println("trying to create user - Service");
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // MUST encode
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));
    }
}