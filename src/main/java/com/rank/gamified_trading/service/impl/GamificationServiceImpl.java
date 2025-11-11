package com.rank.gamified_trading.service.impl;

import com.rank.gamified_trading.model.User;
import com.rank.gamified_trading.service.GamificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GamificationServiceImpl implements GamificationService {
    private static final Logger log = LoggerFactory.getLogger(GamificationServiceImpl.class);

    private final UserServiceImpl userServiceImpl;
    private final LeaderboardServiceImpl leaderboardServiceImpl;

     // Triggered after every successful trade (buy or sell)
    public void handleTradeGamification(String userId) {
        User updatedUser = userServiceImpl.awardGemsForTrade(userId);
        leaderboardServiceImpl.updateRankings(); // auto-refresh after gem change
        log.info("Leaderboard updated after trade for user '{}'", updatedUser.getUsername());

    }
}
