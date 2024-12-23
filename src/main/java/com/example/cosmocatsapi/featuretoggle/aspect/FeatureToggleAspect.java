package com.example.cosmocatsapi.featuretoggle.aspect;

import com.example.cosmocatsapi.featuretoggle.FeatureToggleService;
import com.example.cosmocatsapi.featuretoggle.FeatureToggles;
import com.example.cosmocatsapi.featuretoggle.annotation.FeatureToggle;
import com.example.cosmocatsapi.featuretoggle.exception.FeatureNotEnabledException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {
    private final FeatureToggleService featureToggleService;

    @Before(value = "@annotation(featureToggle)")
    public void checkFeatureToggle(FeatureToggle featureToggle){
        FeatureToggles toggle = featureToggle.value();

        if(!featureToggleService.checkFeatureToggle(toggle.getName())) {
            throw new FeatureNotEnabledException(toggle.getName());
        }
    }
}