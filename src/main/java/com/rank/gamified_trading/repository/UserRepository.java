package com.rank.gamified_trading.repository;

import com.rank.gamified_trading.model.User;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Getter
public class UserRepository {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public Optional<User> findByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username.trim()))
                .findFirst();
    }

    public User save(User user) {
        findByUsername(user.getUsername()).ifPresent(existing -> {
            if (!existing.getUserId().equals(user.getUserId())) {
                throw new IllegalArgumentException("Username already exists: " + user.getUsername());
            }
        });
        users.put(user.getUserId(), user);
        return user;
    }

    public Optional<User> findById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }


    public void clear() {
        users.clear();
    }

}
