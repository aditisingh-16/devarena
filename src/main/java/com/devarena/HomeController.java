package com.devarena;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "🚀 Welcome to DevArena!";
    }

    @GetMapping("/health")
    public String health() {
        return "✅ Server is running!";
    }
}
