package com.rank.gamified_trading.repository;

import com.rank.gamified_trading.domain.Portfolio;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Getter
public class PortfolioRepository {
    private final Map<String, Portfolio> portfolios = new ConcurrentHashMap<>();

    public Portfolio save(Portfolio portfolio) {
        portfolios.put(portfolio.getUserId(), portfolio);
        return portfolio;
    }

    public Optional<Portfolio> findByUserId(String userId) {
        return Optional.ofNullable(portfolios.get(userId));
    }

    public Portfolio getOrCreate(String userId) {
        return portfolios.computeIfAbsent(userId, Portfolio::of);
    }
}