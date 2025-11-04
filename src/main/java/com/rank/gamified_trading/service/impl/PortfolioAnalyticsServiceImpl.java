package com.rank.gamified_trading.service.impl;

import com.rank.gamified_trading.model.Asset;
import com.rank.gamified_trading.dto.response.PortfolioResponse;
import com.rank.gamified_trading.dto.response.TradedAssetResponse;
import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.repository.PortfolioRepository;
import com.rank.gamified_trading.repository.UserRepository;
import com.rank.gamified_trading.service.PortfolioAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioAnalyticsServiceImpl implements PortfolioAnalyticsService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    // User with the highest portfolio value
    public UserResponse getTopInvestor() {
        return userRepository.findAll().stream()
                .map(user -> {
                    var portfolio = portfolioRepository.getOrCreate(user.getUserId());
                    return new AbstractMap.SimpleEntry<>(user, portfolio);
                })
                .max(Comparator.comparingDouble(entry -> entry.getValue().getTotalValue()))
                .map(entry -> {
                    var user = entry.getKey();
                    var portfolio = entry.getValue();
                    return UserResponse.from(user, PortfolioResponse.from(portfolio));
                })
                .orElseThrow(() -> new IllegalStateException("No users found."));
    }


    // Most traded asset
    public List<TradedAssetResponse> getMostTradedAssets() {
        return portfolioRepository.findAll().stream()
                .flatMap(p -> p.getAssets().stream())
                .collect(Collectors.groupingBy(
                        Asset::assetId,
                        Collectors.summingInt(Asset::quantity)
                ))
                .entrySet().stream()
                .map(entry -> new TradedAssetResponse(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingInt(TradedAssetResponse::totalQuantity).reversed())
                .toList();
    }

}
