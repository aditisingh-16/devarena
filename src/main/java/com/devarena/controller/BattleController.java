package com.devarena.controller;

import com.devarena.model.Battle;
import com.devarena.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/battle")
public class BattleController {

    @Autowired
    private BattleService battleService;

    @PostMapping("/join")
    public Battle joinBattle(@RequestParam String username) {
        return battleService.joinBattle(username);
    }

    @GetMapping("/status/{battleId}")
    public Battle getBattleStatus(@PathVariable Long battleId) {
        return battleService.getBattle(battleId);
    }

    @PostMapping("/submit/{battleId}")
    public Battle submitSolution(@PathVariable Long battleId, 
                                  @RequestParam String username) {
        return battleService.submitSolution(battleId, username);
    }
}