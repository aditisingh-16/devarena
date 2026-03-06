package com.devarena.service;

import com.devarena.model.Problem;
import com.devarena.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

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

    public String checkAnswer(Long problemId, String userAnswer) {
        return problemRepository.findById(problemId)
            .map(problem -> {
                if (userAnswer.toLowerCase()
                    .contains(problem.getBugDescription().toLowerCase())) {
                    return "✅ Correct! " + problem.getBugDescription();
                } else {
                    return "❌ Not quite! Hint: " + problem.getHint();
                }
            })
            .orElse("Problem not found!");
    }
}