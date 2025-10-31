package com.rank.gamified_trading.dto.response;

import com.rank.gamified_trading.domain.Asset;
import com.rank.gamified_trading.domain.User;

import java.util.List;

public record UserResponse(
        String userId,
        String username,
        int gemCount,
        int rank,
        int tradeCount,
        double portfolioValue,
        List<AssetDto> assets
) {
    public static UserResponse from(User user, PortfolioResponse portfolio) {
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getGemCount(),
                user.getRank(),
                user.getTradeCount(),
                portfolio.totalValue(),
                portfolio.assets()
        );
    }
}