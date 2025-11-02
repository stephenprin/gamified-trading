package com.rank.gamified_trading.dto.response;

import com.rank.gamified_trading.domain.Asset;
import com.rank.gamified_trading.domain.User;

import java.util.Collections;
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
        double value = (portfolio != null) ? portfolio.totalValue() : 0.0;
        List<AssetDto> assets = (portfolio != null) ? portfolio.assets() : Collections.emptyList();

        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getGemCount(),
                user.getRank(),
                user.getTradeCount(),
                value,
                assets
        );
    }
}