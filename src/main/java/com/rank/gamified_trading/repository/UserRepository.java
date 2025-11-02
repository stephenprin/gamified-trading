package com.rank.gamified_trading.repository;

import com.rank.gamified_trading.domain.User;
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

    public User save(User user) {
        users.put(user.getUserId(), user);
        return user;
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
