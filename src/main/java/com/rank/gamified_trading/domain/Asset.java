package com.rank.gamified_trading.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class Asset {
    @EqualsAndHashCode.Include
    private final String assetId;
    private final String name;
    private final int quantity;
    private final double price;  // avg price or last buy price

    public double getValue() {
        return quantity * price;
    }

    // Used internally for updates
    public Asset withQuantity(int newQuantity) {
        return new Asset(assetId, name, newQuantity, price);
    }

    public Asset withPrice(double newPrice) {
        return new Asset(assetId, name, quantity, newPrice);
    }
}