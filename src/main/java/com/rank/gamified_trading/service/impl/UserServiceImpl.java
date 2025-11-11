package com.rank.gamified_trading.service.impl;

import com.rank.gamified_trading.model.User;
import com.rank.gamified_trading.dto.response.PortfolioResponse;
import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.repository.UserRepository;
import com.rank.gamified_trading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PortfolioServiceImpl portfolioServiceImpl;

    public UserResponse createUser(String username) {
        User user = User.create(username);
        userRepository.save(user);
        log.debug("✅ User '{}' created successfully with ID: {}", user.getUsername(), user.getUserId());
        return UserResponse.from(user, portfolioServiceImpl.getPortfolio(user.getUserId()));
    }

    public UserResponse getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new IllegalArgumentException("User not found: " + userId);
                });

        PortfolioResponse portfolio = portfolioServiceImpl.getPortfolio(userId);
        return UserResponse.from(user, portfolio);
    }

    public User awardGemsForTrade(String userId) {


        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Cannot award gems — user not found: {}", userId);
                    return new IllegalArgumentException("User not found");
                });

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
        log.info("User '{}' gem count updated successfully", user.getUsername());
        return userRepository.save(user);
    }

    public void handleTradeStreakTimeReset(User user) {
        Instant now = Instant.now();

        if (user.getLastTradeAt() == null ||
                Duration.between(user.getLastTradeAt(), now).toHours() >= 24) {
            log.debug("Resetting streak for user '{}' — last trade was over 24 hours ago or first trade.", user.getUsername());
            user.resetStreak();
        }

        // Update the last trade timestamp
        user.setLastTradeAt(now);
    }

}
