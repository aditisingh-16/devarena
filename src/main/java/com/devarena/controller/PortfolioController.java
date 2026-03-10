package com.devarena.controller;

import com.devarena.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/analyze/{username}")
    public Map<String, Object> analyzePortfolio(@PathVariable String username) {
        return portfolioService.analyzePortfolio(username);
    }
}