package com.rank.gamified_trading.controller;

import com.rank.gamified_trading.dto.request.CreateUserRequest;
import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // POST /users
    public Map<String, Object> createUser(@Valid CreateUserRequest request) {
        UserResponse response = userService.createUser(request.username().trim());
        return Map.of(
                "success", true,
                "data", response
        );
    }

    // GET /users/{id}
    public Map<String, Object> getUser(String userId) {
        try {
            UserResponse response = userService.getUser(userId);
            return Map.of("success", true, "data", response);
        } catch (IllegalArgumentException e) {
            return Map.of("success", false, "error", e.getMessage());
        }
    }

    // Helper: Handle validation errors (if you want centralized handling)
    public static Map<String, Object> handleValidationError(List<String> errors) {
        return Map.of(
                "success", false,
                "error", "Validation failed",
                "details", errors
        );
    }
}