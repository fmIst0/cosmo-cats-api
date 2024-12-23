package com.example.cosmocatsapi.web;

import com.example.cosmocatsapi.domain.CosmoCat;
import com.example.cosmocatsapi.featuretoggle.FeatureToggles;
import com.example.cosmocatsapi.featuretoggle.annotation.FeatureToggle;
import com.example.cosmocatsapi.service.CosmoCatsService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cosmo-cats")
public class CosmoCatsController {
    private final CosmoCatsService cosmoCatsService;

    public CosmoCatsController(CosmoCatsService cosmoCatsService) {
        this.cosmoCatsService = cosmoCatsService;
    }

    @GetMapping
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public ResponseEntity<List<CosmoCat>> getCosmoCats() {
        return ResponseEntity.ok(cosmoCatsService.getCosmoCats());
    }
}