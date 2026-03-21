package com.devarena.repository;

import com.devarena.model.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BattleRepository extends JpaRepository<Battle, Long> {
    Optional<Battle> findByStatus(String status);
}