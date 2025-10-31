package com.rank.gamified_trading.domain;


import lombok.*;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    @EqualsAndHashCode.Include
    private final String userId;
    private final String username;
    private int gemCount = 0;
    @Setter
    private int rank = 0;
    private int tradeCount = 0;

    private final Portfolio portfolio;


    public static User create(String username) {
        String userId = UUID.randomUUID().toString();
        return new User(
                userId,
                username.trim(),
                Portfolio.of(userId) 
        );
    }

    public void addGems(int gems) {
        if (gems < 0) throw new IllegalArgumentException("Gems cannot be negative");
        this.gemCount += gems;
    }

    public void incrementTradeCount() {
        this.tradeCount++;
    }


}