package com.rank.gamified_trading.service;

import com.rank.gamified_trading.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GamificationService {

    private final UserService userService;
    private final LeaderboardService leaderboardService;

     // Triggered after every successful trade (buy or sell)
    public void handleTradeGamification(String userId) {
        User updatedUser = userService.awardGemsForTrade(userId);
        leaderboardService.updateRankings(); // auto-refresh after gem change

        // Later: you’ll call leaderboardService.updateRankings(updatedUser)
        // For now, just log or print gem progress.
        System.out.printf("User %s now has %d gems and rank #%d%n",
                updatedUser.getUsername(), updatedUser.getGemCount(), updatedUser.getRank());
    }
}
