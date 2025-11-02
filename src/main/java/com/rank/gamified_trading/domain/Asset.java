package com.rank.gamified_trading.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    public double calculateMarketValue() {
        return quantity * price;
    }

    // Used internally for updates
    public Asset adjustQuantity(int newQuantity) {
        return new Asset(assetId, name, newQuantity, price);
    }

    public Asset updatePrice(double newPrice) {
        return new Asset(assetId, name, quantity, newPrice);
    }
}