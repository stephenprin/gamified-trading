package com.rank.gamified_trading.service;

import com.rank.gamified_trading.domain.User;
import com.rank.gamified_trading.dto.response.PortfolioResponse;
import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.repository.PortfolioRepository;
import com.rank.gamified_trading.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PortfolioService portfolioService;

    public UserResponse createUser(String username) {
        User user = User.create(username);
        userRepository.save(user);
        return UserResponse.from(user, portfolioService.getPortfolio(user.getUserId()));
    }

    public UserResponse getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        PortfolioResponse portfolio = portfolioService.getPortfolio(userId);
        return UserResponse.from(user, portfolio);
    }


    public void updateUser(User user) {
        userRepository.save(user);
    }
}
