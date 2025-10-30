package com.rank.gamified_trading.repository;

import com.rank.gamified_trading.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@RequiredArgsConstructor
public class UserRepository {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public void save(User user) {
        users.put(user.getUserId(), user);
    }

    public Optional<User> findById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public boolean existsById(String userId) {
        return users.containsKey(userId);
    }

    public long count() {
        return users.size();
    }
}
