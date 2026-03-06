package com.devarena.controller;

import com.devarena.model.Problem;
import com.devarena.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @PostMapping("/add")
    public Problem addProblem(@RequestBody Problem problem) {
        return problemService.addProblem(problem);
    }

    @GetMapping("/all")
    public List<Problem> getAllProblems() {
        return problemService.getAllProblems();
    }

    @GetMapping("/{id}")
    public Object getProblemById(@PathVariable Long id) {
        return problemService.getProblemById(id)
            .map(p -> (Object) p)
            .orElse("Problem not found!");
    }

    @GetMapping("/category/{category}")
    public List<Problem> getByCategory(@PathVariable String category) {
        return problemService.getProblemsByCategory(category);
    }

    @GetMapping("/difficulty/{difficulty}")
    public List<Problem> getByDifficulty(@PathVariable String difficulty) {
        return problemService.getProblemsByDifficulty(difficulty);
    }

    @PostMapping("/check")
    public String checkAnswer(
        @RequestParam Long problemId,
        @RequestParam String userAnswer
    ) {
        return problemService.checkAnswer(problemId, userAnswer);
    }
}
