package com.example.cosmocatsapi.featuretoggle;

import lombok.Getter;

@Getter
public enum FeatureToggles {
    COSMO_CATS("cosmo-cats"),
    KITTY_PRODUCTS("kitty-products");

    private final String name;

    FeatureToggles(String name) {
        this.name = name;
    }
}