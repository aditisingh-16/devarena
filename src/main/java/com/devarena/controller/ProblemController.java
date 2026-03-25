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
    @RequestParam String userAnswer,
    @RequestParam(required = false) String username
    ) {
        return problemService.checkAnswer(problemId, userAnswer, username);
    }
    @GetMapping("/random")
    public Object getRandomProblem(@RequestParam(required = false) String username) {
    List<Problem> problems = problemService.getAllProblems();
    if (problems.isEmpty()) {
        return new java.util.HashMap<String, String>() {{ put("error", "No problems found"); }};
    }
    int idx = (int)(Math.random() * problems.size());
    return problems.get(idx);
}
}
