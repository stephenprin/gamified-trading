package com.rank.gamified_trading.dto.response;

import com.rank.gamified_trading.domain.Asset;
import com.rank.gamified_trading.domain.Portfolio;

import java.util.List;

public record PortfolioResponse(
        String userId,
        List<AssetDto> assets,
        double totalValue
) {
    public static PortfolioResponse from(Portfolio portfolio) {
        var assets = portfolio.getAssets().stream()
                .map(AssetDto::from)
                .toList();
        return new PortfolioResponse(portfolio.getUserId(), assets, portfolio.getTotalValue());
    }
}

record AssetDto(
        String assetId,
        String name,
        int quantity,
        double price,
        double value
) {
    static AssetDto from(Asset a) {
        return new AssetDto(a.getAssetId(), a.getName(), a.getQuantity(), a.getPrice(), a.calculateMarketValue());
    }
}
