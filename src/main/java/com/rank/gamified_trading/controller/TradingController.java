package com.rank.gamified_trading.controller;

import com.rank.gamified_trading.dto.request.TradeRequest;
import com.rank.gamified_trading.dto.response.PortfolioResponse;
import com.rank.gamified_trading.service.TradingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trades")
@RequiredArgsConstructor
public class TradingController {

    private final TradingService tradingService;

    @PostMapping("/{userId}/buy")
    public ResponseEntity<PortfolioResponse> buyAsset(
            @PathVariable String userId,
            @Valid @RequestBody TradeRequest request) {
        return ResponseEntity.ok(tradingService.buyAsset(userId, request));
    }

    @PostMapping("/{userId}/sell")
    public ResponseEntity<PortfolioResponse> sellAsset(
            @PathVariable String userId,
            @Valid @RequestBody TradeRequest request) {
        return ResponseEntity.ok(tradingService.sellAsset(userId, request));
    }
}
