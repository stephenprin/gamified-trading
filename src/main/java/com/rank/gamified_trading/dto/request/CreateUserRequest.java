package com.rank.gamified_trading.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(@NotBlank(message = "Username is required")
                                @Size(min = 3, max = 50, message = "Username must be 3-50 characters")
                                @Pattern(regexp = "[a-zA-Z0-9_]+", message = "Only letters, numbers, underscore")
                                String username) { }
