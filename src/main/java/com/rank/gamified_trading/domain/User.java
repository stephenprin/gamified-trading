package com.rank.gamified_trading.domain;


import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    @EqualsAndHashCode.Include
    private final String userId;
    private final String username;
    private int gemCount = 0;
    private int rank = 0;
    private int tradeCount = 0;

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


}