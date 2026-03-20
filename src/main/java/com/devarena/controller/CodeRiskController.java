package com.devarena.controller;

import com.devarena.service.CodeRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coderisk")
public class CodeRiskController {

    @Autowired
    private CodeRiskService codeRiskService;

    @PostMapping("/analyze")
    public String analyzeCode(@RequestParam String code, 
                               @RequestParam String language) {
        return codeRiskService.analyzeRisks(code, language);
    }
}