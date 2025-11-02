package com.rank.gamified_trading.dto.response;

public record TradedAssetResponse(
        String assetId,
        int totalQuantity
) {
    public int totalQuantity() { return totalQuantity; }
}
