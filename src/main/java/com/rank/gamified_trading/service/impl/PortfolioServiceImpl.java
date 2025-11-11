package com.rank.gamified_trading.service.impl;

import com.rank.gamified_trading.repository.AssetCatalog;
import com.rank.gamified_trading.model.Portfolio;
import com.rank.gamified_trading.dto.response.PortfolioResponse;
import com.rank.gamified_trading.exception.AssetNotFoundException;
import com.rank.gamified_trading.repository.PortfolioRepository;
import com.rank.gamified_trading.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private static final Logger log = LoggerFactory.getLogger(PortfolioServiceImpl.class);

    private final PortfolioRepository portfolioRepository;
    private final AssetCatalog assetCatalog;

    public PortfolioResponse addAsset(String userId, String assetId, int quantity) {
        var asset = assetCatalog.get(assetId);
        if (asset == null) {
            log.error("Asset not found in catalog: {}", assetId);
            throw new AssetNotFoundException("Invalid asset: " + assetId);
        }

        Portfolio portfolio = portfolioRepository.getOrCreate(userId);
        portfolio.addAsset(asset.assetId(), asset.name(), quantity, asset.currentPrice());
        Portfolio updated = portfolioRepository.save(portfolio);
        return PortfolioResponse.from(updated);
    }

    public void removeAsset(String userId, String assetId, int quantity) {
        Portfolio portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found"));
        portfolio.removeAsset(assetId, quantity);
        portfolioRepository.save(portfolio);
        log.debug("Removed {} of asset {} from user {}'s portfolio", quantity, assetId, userId);
    }

    public PortfolioResponse getPortfolio(String userId) {
        Portfolio portfolio = portfolioRepository.findByUserId(userId)
                .orElse(Portfolio.of(userId));
        return PortfolioResponse.from(portfolio);
    }

}
