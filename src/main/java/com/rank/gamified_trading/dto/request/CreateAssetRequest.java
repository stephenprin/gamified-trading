package com.rank.gamified_trading.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateAssetRequest(
        @NotBlank(message = "Asset name is required")
        String name,

        @Positive(message = "Quantity must be greater than 0")
        int quantity,

        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        BigDecimal price
) {}
