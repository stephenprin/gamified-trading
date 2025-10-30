package com.rank.gamified_trading.controller;

import com.rank.gamified_trading.dto.request.CreateUserRequest;
import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users") // base path
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public Map<String, Object> createUser(@Valid CreateUserRequest request) {
        UserResponse response = userService.createUser(request.username().trim());
        return Map.of(
                "success", true,
                "data", response
        );
    }

    @GetMapping("/{id}")
    public Map<String, Object> getUser(String userId) {
        try {
            UserResponse response = userService.getUser(userId);
            return Map.of("success", true, "data", response);
        } catch (IllegalArgumentException e) {
            return Map.of("success", false, "error", e.getMessage());
        }
    }
}