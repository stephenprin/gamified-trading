package com.rank.gamified_trading.service;

import com.rank.gamified_trading.domain.User;
import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse createUser(String username) {
        User user = User.create(username);
        userRepository.save(user);
        return UserResponse.from(user);
    }

    public UserResponse getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        return UserResponse.from(user);
    }

    public User getUserEntity(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
