package com.rank.gamified_trading.service;

import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.model.User;

public interface UserService {

   UserResponse createUser(String username);
    UserResponse getUser(String userId);
    User awardGemsForTrade(String userId);
}
