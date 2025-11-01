package com.rank.gamified_trading.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record TradeRequest(
        @NotBlank(message = "Asset ID is required")
        String assetId,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
) {}
