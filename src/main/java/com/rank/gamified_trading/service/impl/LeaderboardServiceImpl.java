package com.rank.gamified_trading.service.impl;

import com.rank.gamified_trading.model.User;
import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.repository.UserRepository;
import com.rank.gamified_trading.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {

    private final UserRepository userRepository;
    private final List<User> leaderboard = new CopyOnWriteArrayList<>();


    //Update leaderboard when a user's gem count changes.
    public synchronized void updateRankings() {
        List<User> users = userRepository.findAll();
        users.sort(Comparator.comparingInt(User::getGemCount)
                .reversed()
                .thenComparing(User::getUsername));

        leaderboard.clear();
        leaderboard.addAll(users);

        assignRanks(users);
    }


     // Assign ranks dynamically (ties share same rank)
    private void assignRanks(List<User> users) {
        int currentRank = 1;
        int previousGemCount = -1;

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getGemCount() != previousGemCount) {
                currentRank = i + 1;
                previousGemCount = user.getGemCount();
            }
            user.setRank(currentRank);
            userRepository.save(user);
        }
    }

     public List<UserResponse> getTopN(int n) {
         return leaderboard.stream()
                 .limit(n)
                 .map(user -> UserResponse.from(user, null))
                 .collect(Collectors.toList());
     }

    public List<UserResponse> getAllRankedUsers() {
        List<User> users = userRepository.findAll().stream()
                .sorted(Comparator.comparingInt(User::getGemCount)
                        .reversed()
                        .thenComparing(User::getUsername))
                .toList();

        int currentRank = 1;
        int previousGemCount = -1;

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getGemCount() != previousGemCount) {
                currentRank = i + 1;
                previousGemCount = user.getGemCount();
            }
            user.setRank(currentRank);
        }

        return users.stream()
                .map(user -> UserResponse.from(user, null))
                .collect(Collectors.toList());
    }

}