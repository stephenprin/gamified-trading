package com.rank.gamified_trading.controller;

import com.rank.gamified_trading.dto.request.TradeRequest;
import com.rank.gamified_trading.dto.response.PortfolioResponse;
import com.rank.gamified_trading.service.TradingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trades/{userId}")
@Tag(
        name = "Trading Operations",
        description = "Endpoints for executing buy and sell trades, updating portfolios, and triggering gem rewards."
)
@RequiredArgsConstructor
public class TradingController {

    private final TradingService tradingService;

    @PostMapping("/buy")
    public ResponseEntity<PortfolioResponse> buyAsset(
            @PathVariable String userId,
            @Valid @RequestBody TradeRequest request) {
        return ResponseEntity.ok(tradingService.buyAsset(userId, request));
    }

    @PostMapping("/sell")
    public ResponseEntity<PortfolioResponse> sellAsset(
            @PathVariable String userId,
            @Valid @RequestBody TradeRequest request) {
        return ResponseEntity.ok(tradingService.sellAsset(userId, request));
    }
}
