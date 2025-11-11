package com.rank.gamified_trading.controller;


import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.service.LeaderboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
@Tag(
        name = "Leaderboard Management",
        description = "Endpoints for managing and retrieving ranked users based on gem counts and achievements."
)
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/top/{n}")
    public ResponseEntity<List<UserResponse>> getTopN(@PathVariable int n) {
        return ResponseEntity.ok(leaderboardService.getTopN(n));
    }
}
