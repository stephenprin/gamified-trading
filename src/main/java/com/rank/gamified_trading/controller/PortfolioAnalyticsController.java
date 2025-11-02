package com.rank.gamified_trading.controller;

import com.rank.gamified_trading.dto.response.TradedAssetResponse;
import com.rank.gamified_trading.dto.response.UserResponse;
import com.rank.gamified_trading.service.PortfolioAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/insights")
@RequiredArgsConstructor
public class PortfolioAnalyticsController {

    private final PortfolioAnalyticsService analyticsService;

    @GetMapping("/top-investor")
    public ResponseEntity<UserResponse> getTopInvestor() {
        return ResponseEntity.ok(analyticsService.getTopInvestor());
    }

    @GetMapping("/most-traded-assets")
    public ResponseEntity<List<TradedAssetResponse>> getMostTradedAssets() {
        return ResponseEntity.ok(analyticsService.getMostTradedAssets());
    }
}
