package com.rank.gamified_trading.dto.response.exception;

public class AssetNotFoundException extends RuntimeException {

    public AssetNotFoundException(String assetId) {
        super("Asset not found in catalog: " + assetId);
    }
}