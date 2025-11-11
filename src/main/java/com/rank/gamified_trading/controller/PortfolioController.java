package com.rank.gamified_trading.controller;

import com.rank.gamified_trading.dto.request.AssetRequest;
import com.rank.gamified_trading.dto.response.PortfolioResponse;
import com.rank.gamified_trading.service.PortfolioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portfolio/{userId}")
@Tag(
        name = "Portfolio Management",
        description = "Endpoints for managing user portfolios — adding, removing, and viewing assets."
)
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping("/assets")
    public ResponseEntity<PortfolioResponse> addAsset(
            @PathVariable String userId,
            @Valid @RequestBody AssetRequest request
    ) {
        var response= portfolioService.addAsset(userId, request.assetId(), request.quantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/assets/{assetId}")
    public ResponseEntity<Void> removeAsset(
            @PathVariable String userId,
            @PathVariable String assetId,
            @RequestParam int quantity
    ) {
        portfolioService.removeAsset(userId, assetId, quantity);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/assets")
    public ResponseEntity<PortfolioResponse> getPortfolio(@PathVariable String userId) {
        PortfolioResponse response = portfolioService.getPortfolio(userId);
        return ResponseEntity.ok(response);
    }
}
