package com.rank.gamified_trading.domain;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
public class AssetCatalog {
    private final Map<String, AssetInfo> catalog = new ConcurrentHashMap<>();

    public record AssetInfo(String assetId, String name, double currentPrice) {}

    public AssetCatalog() {
        add("BTC", "Bitcoin", 50000.0);
        add("ETH", "Ethereum", 3000.0);
        add("AAPL", "Apple Inc.", 175.0);
        add("GOOGL", "Alphabet Inc.", 2800.0);
        add("AMZN", "Amazon.com Inc.", 3400.0);
    }

    private void add(String id, String name, double price) {
        catalog.put(id, new AssetInfo(id, name, price));
    }

    public AssetInfo get(String assetId) {
        return catalog.get(assetId);
    }

    public boolean exists(String assetId) {
        return catalog.containsKey(assetId);
    }
}
