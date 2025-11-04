package com.rank.gamified_trading.repository;

import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
public class AssetCatalog {
    private final Map<String, AssetInfo> catalog = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public record AssetInfo(String assetId, String name, double currentPrice) {}

    public AssetCatalog() {
        add("BTC", "Bitcoin", 50000.00);
        add("ETH", "Ethereum", 3000.00);
        add("AAPL", "Apple Inc.", 175.00);
        add("GOOGL", "Alphabet Inc.", 2800.00);
        add("AMZN", "Amazon.com Inc.", 3400.00);
    }

    private void add(String id, String name, double price) {
        catalog.put(id, new AssetInfo(id, name, price));
    }

    // Simulate dynamic price updates every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void simulatePriceFluctuations() {
        catalog.forEach((id, asset) -> {
            double changePercent = (random.nextDouble() * 4 - 2); // -2% to +2%
            double newPrice = asset.currentPrice() * (1 + changePercent / 100);

            double roundedPrice = Math.round(newPrice * 100.0) / 100.0;
            catalog.put(id, new AssetInfo(asset.assetId(), asset.name(), Math.max(1.00, roundedPrice)));
        });
        System.out.println("Asset prices updated dynamically.");
    }

    public AssetInfo get(String assetId) {
        return catalog.get(assetId);
    }

}
