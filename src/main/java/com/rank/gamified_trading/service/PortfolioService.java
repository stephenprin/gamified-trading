package com.rank.gamified_trading.service;

import com.rank.gamified_trading.domain.AssetCatalog;
import com.rank.gamified_trading.domain.Portfolio;
import com.rank.gamified_trading.dto.response.PortfolioResponse;
import com.rank.gamified_trading.exception.AssetNotFoundException;
import com.rank.gamified_trading.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final AssetCatalog assetCatalog;



    public PortfolioResponse addAsset(String userId, String assetId, int quantity) {
        var asset = assetCatalog.get(assetId);
        if (asset == null) throw new AssetNotFoundException("Invalid asset: " + assetId);

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
    }

    public PortfolioResponse getPortfolio(String userId) {
        Portfolio portfolio = portfolioRepository.findByUserId(userId)
                .orElse(Portfolio.of(userId));
        return PortfolioResponse.from(portfolio);
    }

}
