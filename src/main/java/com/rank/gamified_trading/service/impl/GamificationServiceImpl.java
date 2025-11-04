package com.rank.gamified_trading.service.impl;

import com.rank.gamified_trading.model.User;
import com.rank.gamified_trading.service.GamificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GamificationServiceImpl implements GamificationService {

    private final UserServiceImpl userServiceImpl;
    private final LeaderboardServiceImpl leaderboardServiceImpl;

     // Triggered after every successful trade (buy or sell)
    public void handleTradeGamification(String userId) {
        User updatedUser = userServiceImpl.awardGemsForTrade(userId);
        leaderboardServiceImpl.updateRankings(); // auto-refresh after gem change

        System.out.printf("User %s now has %d gems and rank #%d%n",
                updatedUser.getUsername(), updatedUser.getGemCount(), updatedUser.getRank());
    }
}
