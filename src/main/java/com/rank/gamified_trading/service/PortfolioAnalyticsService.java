package com.rank.gamified_trading.service;

import com.rank.gamified_trading.domain.Asset;
import com.rank.gamified_trading.domain.Portfolio;
import com.rank.gamified_trading.dto.response.PortfolioResponse;
import com.rank.gamified_trading.dto.response.TradedAssetResponse;
import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.repository.PortfolioRepository;
import com.rank.gamified_trading.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.rank.gamified_trading.domain.User;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioAnalyticsService {

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
                        Asset::getAssetId,
                        Collectors.summingInt(Asset::getQuantity)
                ))
                .entrySet().stream()
                .map(entry -> new TradedAssetResponse(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingInt(TradedAssetResponse::totalQuantity).reversed())
                .toList();
    }

}
