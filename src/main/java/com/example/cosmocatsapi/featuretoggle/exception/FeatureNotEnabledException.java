package com.example.cosmocatsapi.featuretoggle.exception;

public class FeatureNotEnabledException extends RuntimeException {
    private static final String FEATURE_NOT_ENABLED = "Feature not enabled";

    public FeatureNotEnabledException(String message) {
        super(String.format(FEATURE_NOT_ENABLED, message));
    }
}