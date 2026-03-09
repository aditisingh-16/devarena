package com.devarena.controller;

import com.devarena.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(
        @RequestParam String username,
        @RequestParam String email,
        @RequestParam String password
    ) {
        return userService.registerUser(username, email, password);
    }

    @PostMapping("/login")
    public String login(
        @RequestParam String username,
        @RequestParam String password
    ) {
        return userService.loginUser(username, password);
    }

    @GetMapping("/all")
    public Object getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/score")
    public String addScore(
        @RequestParam String username,
        @RequestParam int points
    ) {
        return userService.addScore(username, points);
    }

    @GetMapping("/profile/{username}")
    public Object getUserProfile(@PathVariable String username) {
        return userService.getUserProfile(username);
    }
}