package com.rank.gamified_trading.service.impl;

import com.rank.gamified_trading.model.User;
import com.rank.gamified_trading.repository.UserRepository;
import com.rank.gamified_trading.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = new UserRepository();
        userService = new UserServiceImpl(userRepository, null);
    }

    @Test
    void testAwardsOneGemPerTrade() {
        User user = User.create("Prince");
        userRepository.save(user);

        userService.awardGemsForTrade(user.getUserId());
        User updated = userRepository.findById(user.getUserId()).get();
        assertEquals(1, updated.getGemCount());
        assertEquals(1, updated.getTradeCount());
    }

    @Test
    void testAppliesMilestoneBonus() {
        User user = User.create("Prince");
        userRepository.save(user);

        // Simulate reaching milestone (e.g 10th trade)
        for (int i = 0; i < 9; i++) {
            user.incrementTradeCount();
        }
        userRepository.save(user);

        userService.awardGemsForTrade(user.getUserId());

        User updated = userRepository.findById(user.getUserId()).get();

        assertTrue(updated.getGemCount() > 1, "Should include milestone bonus gems");
    }

    @Test
    void testStreakBonusAfterThreeConsecutiveTrades() {
        User user = User.create("Prince");
        userRepository.save(user);

        // Simulate consecutive trades
        for (int i = 0; i < 2; i++) {
            userService.awardGemsForTrade(user.getUserId());
        }
        int before = userRepository.findById(user.getUserId()).get().getGemCount();

        userService.awardGemsForTrade(user.getUserId());
        User updated = userRepository.findById(user.getUserId()).get();

        assertTrue(updated.getGemCount() > before + 1, "Streak bonus should be applied on 3rd trade");
    }

    @Test
    void testStreakResetsAfter24Hours() {
        User user = User.create("Nmezi");
        user.setLastTradeAt(Instant.now().minusSeconds(60 * 60 * 25)); // 25h ago
        userRepository.save(user);

        user.recordTradeStreak(); // had a streak
        assertTrue(user.getCurrentStreak() > 0);

        userService.awardGemsForTrade(user.getUserId());
        User updated = userRepository.findById(user.getUserId()).get();

        assertEquals(1, updated.getCurrentStreak(), "Streak should reset after 24h inactivity");
    }

    @Test
    void testThrowsIfUserNotFound() {
        assertThrows(IllegalArgumentException.class, () -> userService.awardGemsForTrade("nonexistent"));
    }

}