package com.rank.gamified_trading.service.impl;

import com.rank.gamified_trading.model.Asset;
import com.rank.gamified_trading.repository.AssetCatalog;
import com.rank.gamified_trading.model.Portfolio;
import com.rank.gamified_trading.dto.request.TradeRequest;
import com.rank.gamified_trading.dto.response.PortfolioResponse;
import com.rank.gamified_trading.exception.AssetNotFoundException;
import com.rank.gamified_trading.repository.PortfolioRepository;
import com.rank.gamified_trading.service.TradingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradingServiceImpl implements TradingService {
    private static final Logger log = LoggerFactory.getLogger(TradingServiceImpl.class);

    private final PortfolioRepository portfolioRepository;
    private final GamificationServiceImpl gamificationServiceImpl;
    private final AssetCatalog assetCatalog;


    public PortfolioResponse buyAsset(String userId, TradeRequest request) {
        var assetInfo = assetCatalog.get(request.assetId());
        if (assetInfo == null) {
            log.error("Asset not found in catalog: {}", request.assetId());
            throw new AssetNotFoundException(request.assetId());
        }

        Portfolio portfolio = portfolioRepository.getOrCreate(userId);

        portfolio.addAsset(assetInfo.assetId(), assetInfo.name(), request.quantity(), assetInfo.currentPrice());

        portfolioRepository.save(portfolio);
        // Award gems and milestone bonuses
        gamificationServiceImpl.handleTradeGamification(userId);
        log.debug("Gamification processed for user {}", userId);
        return PortfolioResponse.from(portfolio);
    }

    public PortfolioResponse sellAsset(String userId, TradeRequest request) {
        Portfolio portfolio = portfolioRepository.getOrCreate(userId);
        Asset existing = portfolio.findAssetById(request.assetId());

        if (existing == null) {
            throw new IllegalArgumentException("You do not own this asset: " + request.assetId());
        }

        if (existing.quantity() < request.quantity()) {
            log.error("Insufficient quantity: User {} tried to sell {}x{}, owns only {}",
                    userId, request.quantity(), request.assetId(), existing.quantity());
            throw new IllegalArgumentException("Not enough quantity to sell. You own: " + existing.quantity());
        }

        portfolio.removeAsset(request.assetId(), request.quantity());
        portfolioRepository.save(portfolio);

        gamificationServiceImpl.handleTradeGamification(userId);
        log.debug("Gamification processed after SELL for user {}", userId);
        return PortfolioResponse.from(portfolio);
    }
}
