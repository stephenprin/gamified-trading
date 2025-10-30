package com.rank.gamified_trading.dto.response;

import com.rank.gamified_trading.domain.User;
public record UserResponse(

        String username,
        int gemCount,
        int rank,
        int tradeCount
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getUsername(),
                user.getGemCount(),
                user.getRank(),
                user.getTradeCount()
        );
    }
}
