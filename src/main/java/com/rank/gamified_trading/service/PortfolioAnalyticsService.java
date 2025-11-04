package com.rank.gamified_trading.service;

import com.rank.gamified_trading.dto.response.TradedAssetResponse;
import com.rank.gamified_trading.dto.response.UserResponse;

import java.util.List;

public interface PortfolioAnalyticsService {
    UserResponse getTopInvestor();
    List<TradedAssetResponse> getMostTradedAssets();
}
