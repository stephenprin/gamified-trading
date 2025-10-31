package com.rank.gamified_trading.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AssetRequest(
        @NotBlank(message = "Asset ID is required")
        String assetId,

        @Positive(message = "Quantity must be greater than 0")
        int quantity
) {}
