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
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = new UserRepository();
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
    @Test
    void testRankAssignmentAfterUpdate() {
        List<UserResponse> rankedUsers = leaderboardService.getAllRankedUsers();

        assertEquals(3, rankedUsers.size(), "Should have 3 ranked users");
        assertTrue(rankedUsers.get(0).gemCount() >= rankedUsers.get(1).gemCount());
        assertTrue(rankedUsers.get(1).gemCount() >= rankedUsers.get(2).gemCount());

        assertEquals(1, rankedUsers.get(0).rank());
        assertEquals(1, rankedUsers.get(1).rank());
        assertEquals(3, rankedUsers.get(2).rank());
    }

    @Test
    void testRankingAfterGemChange() {
        User prince = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals("Prince"))
                .findFirst()
                .orElseThrow();

        prince.addGems(15);
        leaderboardService.updateRankings();

        List<UserResponse> ranked = leaderboardService.getAllRankedUsers();
        assertEquals("Prince", ranked.get(0).username());
        assertEquals(1, ranked.get(0).rank());
    }

    @Test
    void testEmptyLeaderboard() {
        UserRepository emptyRepo = new UserRepository();
        LeaderboardService emptyLeaderboard = new LeaderboardServiceImpl(emptyRepo);

        List<UserResponse> results = emptyLeaderboard.getTopN(5);
        assertTrue(results.isEmpty(), "Expected empty leaderboard for no users");
    }

    @Test
    void testGetTopNMoreThanUsersAvailable() {
        List<UserResponse> top = leaderboardService.getTopN(10);
        assertEquals(3, top.size(), "Should only return existing users");
    }

    @Test
    void testUsersWithZeroGemsStillIncluded() {
        User newUser = User.create("TestZeroUser");
        userRepository.save(newUser);
        leaderboardService.updateRankings();

        List<UserResponse> ranked = leaderboardService.getAllRankedUsers();
        assertTrue(ranked.stream().anyMatch(u -> u.username().equals("TestZeroUser")));
    }


}