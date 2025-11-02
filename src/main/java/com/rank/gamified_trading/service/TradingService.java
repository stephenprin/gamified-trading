package com.rank.gamified_trading.service;

import com.rank.gamified_trading.domain.Asset;
import com.rank.gamified_trading.domain.AssetCatalog;
import com.rank.gamified_trading.domain.Portfolio;
import com.rank.gamified_trading.dto.request.TradeRequest;
import com.rank.gamified_trading.dto.response.PortfolioResponse;
import com.rank.gamified_trading.exception.AssetNotFoundException;
import com.rank.gamified_trading.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradingService {

    private final PortfolioRepository portfolioRepository;
    private final GamificationService gamificationService;
    private final AssetCatalog assetCatalog;


    public PortfolioResponse buyAsset(String userId, TradeRequest request) {
        var assetInfo = assetCatalog.get(request.assetId());
        if (assetInfo == null) throw new AssetNotFoundException(request.assetId());

        Portfolio portfolio = portfolioRepository.getOrCreate(userId);

        // Add or increase asset quantity
        portfolio.addAsset(assetInfo.assetId(), assetInfo.name(), request.quantity(), assetInfo.currentPrice());

        portfolioRepository.save(portfolio);
        // 🎯 Award gems and milestone bonuses
        gamificationService.handleTradeGamification(userId);
        return PortfolioResponse.from(portfolio);
    }

    public PortfolioResponse sellAsset(String userId, TradeRequest request) {
        Portfolio portfolio = portfolioRepository.getOrCreate(userId);
        Asset existing = portfolio.findAssetById(request.assetId());

        if (existing == null) {
            throw new IllegalArgumentException("You do not own this asset: " + request.assetId());
        }

        if (existing.getQuantity() < request.quantity()) {
            throw new IllegalArgumentException("Not enough quantity to sell. You own: " + existing.getQuantity());
        }

        portfolio.removeAsset(request.assetId(), request.quantity());
        portfolioRepository.save(portfolio);

        gamificationService.handleTradeGamification(userId);
        return PortfolioResponse.from(portfolio);
    }
}
