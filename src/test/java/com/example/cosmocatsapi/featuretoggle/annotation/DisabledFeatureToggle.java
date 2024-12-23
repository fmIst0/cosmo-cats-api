package com.example.cosmocatsapi.featuretoggle.annotation;

import com.example.cosmocatsapi.featuretoggle.FeatureToggles;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DisabledFeatureToggle {
    FeatureToggles value();
}