package com.rank.gamified_trading.service;

import com.rank.gamified_trading.dto.request.TradeRequest;
import com.rank.gamified_trading.dto.response.PortfolioResponse;

public interface TradingService {
    PortfolioResponse buyAsset(String userId, TradeRequest request);
    PortfolioResponse sellAsset(String userId, TradeRequest request);
}
