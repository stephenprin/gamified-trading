package com.rank.gamified_trading.service.impl;

import com.rank.gamified_trading.model.User;
import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.repository.UserRepository;
import com.rank.gamified_trading.service.LeaderboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardServiceImplTest {

    private LeaderboardService leaderboardService;

    @BeforeEach
    void setup() {
        UserRepository userRepository = new UserRepository();
        leaderboardService = new LeaderboardServiceImpl(userRepository);

        User u1 = User.create("Prince");
        u1.addGems(10);

        User u2 = User.create("Pet");
        u2.addGems(20);

        User u3 = User.create("James");
        u3.addGems(20);

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);

        leaderboardService.updateRankings();
    }


    @Test
    void testTopRankedUsers() {
        List<UserResponse> top = leaderboardService.getTopN(2);

        assertEquals("James", top.get(0).username());
        assertEquals("Pet", top.get(1).username());
        assertEquals(top.get(0).gemCount(), top.get(1).gemCount());
    }

}