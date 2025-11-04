package com.rank.gamified_trading.service.impl;

import com.rank.gamified_trading.model.User;
import com.rank.gamified_trading.dto.response.PortfolioResponse;
import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.repository.UserRepository;
import com.rank.gamified_trading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PortfolioServiceImpl portfolioServiceImpl;

    public UserResponse createUser(String username) {
        User user = User.create(username);
        userRepository.save(user);
        return UserResponse.from(user, portfolioServiceImpl.getPortfolio(user.getUserId()));
    }

    public UserResponse getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        PortfolioResponse portfolio = portfolioServiceImpl.getPortfolio(userId);
        return UserResponse.from(user, portfolio);
    }

    public User awardGemsForTrade(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        handleTradeStreakTimeReset(user);

        // 1 gem per trade
        user.incrementTradeCount();
        user.addGems(1);

  

        // milestone bonuses
        int bonus = user.calculateBonusGems();
        if (bonus > 0) {
            user.addGems(bonus);
        }

        //  Track trading streak
        user.recordTradeStreak();
        int streakBonus = user.calculateStreakBonus();
        if (streakBonus > 0) {
            user.addGems(streakBonus);
        }
        return userRepository.save(user);
    }

    public void handleTradeStreakTimeReset(User user) {
        Instant now = Instant.now();

        if (user.getLastTradeAt() == null ||
                Duration.between(user.getLastTradeAt(), now).toHours() >= 24) {
            user.resetStreak(); // reset streak if first trade or last trade was over 24h ago
        }

        // Update the last trade timestamp
        user.setLastTradeAt(now);
    }

}
