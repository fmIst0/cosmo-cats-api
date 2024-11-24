package com.example.cosmocatsapi.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductStatus {
    IN_STOCK("In stock"),
    OUT_OF_STOCK("Out of stock"),
    DISCONTINUED("Discontinued"),
    DISCONTINUING("Discontinuing");

    private final String displayName;
}
