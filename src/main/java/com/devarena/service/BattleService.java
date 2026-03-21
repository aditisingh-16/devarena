package com.devarena.service;

import com.devarena.model.Battle;
import com.devarena.repository.BattleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BattleService {

    @Autowired
    private BattleRepository battleRepository;

    private static final String[][] PROBLEMS = {
        {"Reverse a String", "Write a function that reverses a string. Input: 'hello' Output: 'olleh'"},
        {"Find Maximum", "Write a function to find the maximum number in an array. Input: [3,1,4,1,5] Output: 5"},
        {"Check Palindrome", "Write a function to check if a string is a palindrome. Input: 'racecar' Output: true"},
        {"FizzBuzz", "Print numbers 1-20. For multiples of 3 print 'Fizz', for multiples of 5 print 'Buzz', for both print 'FizzBuzz'"},
        {"Count Vowels", "Write a function that counts the number of vowels in a string. Input: 'hello' Output: 2"}
    };

    public Battle joinBattle(String username) {
        // Check if there's a waiting battle
        Optional<Battle> waitingBattle = battleRepository.findByStatus("WAITING");
        
        if (waitingBattle.isPresent()) {
            Battle battle = waitingBattle.get();
            if (!battle.getPlayer1().equals(username)) {
                battle.setPlayer2(username);
                battle.setStatus("ACTIVE");
                return battleRepository.save(battle);
            }
            return battle;
        }

        // Create new battle
        Battle battle = new Battle();
        battle.setPlayer1(username);
        battle.setStatus("WAITING");

        // Pick random problem
        int idx = (int)(Math.random() * PROBLEMS.length);
        battle.setProblemTitle(PROBLEMS[idx][0]);
        battle.setProblemDescription(PROBLEMS[idx][1]);

        return battleRepository.save(battle);
    }

    public Battle getBattle(Long battleId) {
        return battleRepository.findById(battleId).orElse(null);
    }

    public Battle submitSolution(Long battleId, String username) {
        Battle battle = battleRepository.findById(battleId).orElse(null);
        if (battle != null && battle.getStatus().equals("ACTIVE")) {
            battle.setWinner(username);
            battle.setStatus("FINISHED");
            return battleRepository.save(battle);
        }
        return battle;
    }
}