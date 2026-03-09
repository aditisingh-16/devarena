package com.devarena.service;

import com.devarena.model.Problem;
import com.devarena.repository.ProblemRepository;
import com.devarena.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public Problem addProblem(Problem problem) {
        return problemRepository.save(problem);
    }

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public Optional<Problem> getProblemById(Long id) {
        return problemRepository.findById(id);
    }

    public List<Problem> getProblemsByCategory(String category) {
        return problemRepository.findByCategory(category);
    }

    public List<Problem> getProblemsByDifficulty(String difficulty) {
        return problemRepository.findByDifficulty(difficulty);
    }

    public String checkAnswer(Long problemId, String userAnswer, String username) {
        return problemRepository.findById(problemId)
            .map(problem -> {
                String answer = userAnswer.toLowerCase();
    String bugDesc = problem.getBugDescription().toLowerCase();
    String[] keywords = bugDesc.split(" ");
    int matchCount = 0;
    for (String keyword : keywords) {
    if (keyword.length() > 3 && answer.contains(keyword)) {
        matchCount++;
    }
    }
    if (matchCount >= 2) {
                    int points = getPoints(problem.getDifficulty());
                    if (username != null && !username.isEmpty()) {
                        userService.addScore(username, points);
                    }
                    return "✅ Correct! +" + points + " points! " + problem.getBugDescription();
                } else {
                    return "❌ Not quite! Hint: " + problem.getHint();
                }
            })
            .orElse("Problem not found!");
    }

    private int getPoints(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "hard": return 30;
            case "medium": return 20;
            default: return 10;
        }
    }
}