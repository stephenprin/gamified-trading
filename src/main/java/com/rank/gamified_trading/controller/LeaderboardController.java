package com.rank.gamified_trading.controller;


import com.rank.gamified_trading.domain.User;
import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/top/{n}")
    public ResponseEntity<List<UserResponse>> getTopN(@PathVariable int n) {
        return ResponseEntity.ok(leaderboardService.getTopN(n));
    }
}
