package com.rank.gamified_trading.service;

import com.rank.gamified_trading.dto.response.PortfolioResponse;

public interface PortfolioService {
    PortfolioResponse addAsset(String userId, String assetId, int quantity);
    PortfolioResponse getPortfolio(String userId);
    void removeAsset(String userId, String assetId, int quantity);

}
