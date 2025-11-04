package com.rank.gamified_trading.service;

import com.rank.gamified_trading.dto.response.UserResponse;

import java.util.List;

public interface LeaderboardService {
    void updateRankings();
    List<UserResponse> getTopN(int n);
}
