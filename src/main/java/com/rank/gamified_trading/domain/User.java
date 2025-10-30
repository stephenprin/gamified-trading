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

    public static User create(String username) {
        return new User(UUID.randomUUID().toString(), username);
    }

    public void addGems(int gems) {
        if (gems < 0) throw new IllegalArgumentException("Gems cannot be negative");
        this.gemCount += gems;
    }

    public void incrementTradeCount() {
        this.tradeCount++;
    }

}