package com.rank.gamified_trading.model;


import lombok.*;

import java.util.Map;
import java.util.UUID;
import java.time.Instant;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {
    private final String userId;
    private final String username;
    private int gemCount = 0;
    private int rank = 0;
    private int tradeCount = 0;
    private int currentStreak;
    private int longestStreak;
    private Instant lastTradeAt;

    private final Portfolio portfolio;


    // 🎯 Configurable milestone bonuses and can be extended
    private static final Map<Integer, Integer> BONUS_MILESTONES = Map.of(
            5, 5,
            10, 10

    );


    public static User create(String username) {
        String userId = UUID.randomUUID().toString();
        return new User(
                userId,
                username.trim(),
                Portfolio.of(userId)
        );
    }

    public void addGems(int gems) {
        this.gemCount += gems;
    }

    // track trades
    public void incrementTradeCount() {
        this.tradeCount++;
    }

    // Calculate milestone bonuses
    public int calculateBonusGems() {
        return BONUS_MILESTONES.getOrDefault(this.tradeCount, 0);
    }

    public void recordTradeStreak() {
        this.currentStreak++;
        // Update longest streak if beaten
        if (this.currentStreak > this.longestStreak) {
            this.longestStreak = this.currentStreak;
        }
    }

    public void resetStreak() {
        this.currentStreak = 0;
    }

    public int calculateStreakBonus() {
        if (currentStreak % 3 == 0) return currentStreak;
        return 0;
    }

}