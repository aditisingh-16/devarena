package com.devarena.repository;

import com.devarena.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByCategory(String category);
    List<Problem> findByDifficulty(String difficulty);
}