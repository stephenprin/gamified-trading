package com.rank.gamified_trading.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Portfolio {
    private final String userId;
    private final List<Asset> assets = new CopyOnWriteArrayList<>();

    public static Portfolio of(String userId) {
        return new Portfolio(userId);
    }

    // Add or update asset
    public void addAsset(String assetId, String name, int quantity, double price) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");

        Asset existing = findAssetById(assetId);
        if (existing != null) {
            // Update quantity and recalculate avg price
            int totalQty = existing.getQuantity() + quantity;
            double totalCost = (existing.getQuantity() * existing.getPrice()) + (quantity * price);
            double avgPrice = totalQty == 0 ? 0 : totalCost / totalQty;

            Asset updated = new Asset(assetId, name, totalQty, avgPrice);
            assets.remove(existing);
            assets.add(updated);
        } else {
            assets.add(new Asset(assetId, name, quantity, price));
        }
    }

    // Remove or reduce quantity
    public void removeAsset(String assetId, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");

        Asset existing = findAssetById(assetId);
        if (existing == null) {
            throw new IllegalArgumentException("Asset not found: " + assetId);
        }

        int newQty = existing.getQuantity() - quantity;
        if (newQty < 0) {
            throw new IllegalArgumentException("Not enough quantity to remove");
        }

        assets.remove(existing);
        if (newQty > 0) {
            assets.add(existing.withQuantity(newQty));
        }
    }

    // Dynamic portfolio value
    public double getTotalValue() {
        return assets.stream()
                .mapToDouble(Asset::getValue)
                .sum();
    }

    private Asset findAssetById(String assetId) {
        return assets.stream()
                .filter(a -> a.getAssetId().equals(assetId))
                .findFirst()
                .orElse(null);
    }

    public List<Asset> getAssets() {
        return List.copyOf(assets);
    }
}