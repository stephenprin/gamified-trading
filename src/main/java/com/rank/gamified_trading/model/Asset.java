package com.rank.gamified_trading.model;

import lombok.EqualsAndHashCode;


public record Asset(@EqualsAndHashCode.Include String assetId, String name, int quantity, double price) {
    public double calculateMarketValue() {
        return quantity * price;
    }

    public Asset adjustQuantity(int newQuantity) {
        return new Asset(assetId, name, newQuantity, price);
    }

}