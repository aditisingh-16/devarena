package com.devarena.service;

import com.devarena.model.User;
import com.devarena.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String registerUser(String username, String email, String password) {
        if (userRepository.existsByUsername(username)) {
            return "Username already taken!";
        }
        if (userRepository.existsByEmail(email)) {
            return "Email already registered!";
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return "User registered successfully!";
    }

    public String loginUser(String username, String password) {
        return userRepository.findByUsername(username)
            .map(user -> {
                if (user.getPassword().equals(password)) {
                    return "Login successful! Welcome " + user.getUsername();
                } else {
                    return "Wrong password!";
                }
            })
            .orElse("User not found!");
    }

    public Object getAllUsers() {
        return userRepository.findAll();
    }

    public String addScore(String username, int points) {
        return userRepository.findByUsername(username)
            .map(user -> {
                int newScore = user.getScore() + points;
                user.setScore(newScore);
                user.setBadge(calculateBadge(newScore));
                userRepository.save(user);
                return "Score updated! New score: " + newScore + " | Badge: " + user.getBadge();
            })
            .orElse("User not found!");
    }

    public Object getUserProfile(String username) {
        return userRepository.findByUsername(username)
            .map(user -> {
                return "Username: " + user.getUsername() +
                       " | Score: " + user.getScore() +
                       " | Badge: " + user.getBadge();
            })
            .orElse("User not found!");
    }

    private String calculateBadge(int score) {
        if (score >= 200) return "Elite Hacker 👑";
        if (score >= 101) return "Pro Coder 🔥";
        if (score >= 51) return "Developer 💻";
        return "Beginner 🌱";
    }
}