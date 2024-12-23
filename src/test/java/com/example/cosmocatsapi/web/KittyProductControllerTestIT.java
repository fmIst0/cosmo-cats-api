package com.example.cosmocatsapi.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.cosmocatsapi.featuretoggle.annotation.DisabledFeatureToggle;
import com.example.cosmocatsapi.featuretoggle.annotation.EnabledFeatureToggle;
import com.example.cosmocatsapi.featuretoggle.FeatureToggleExtension;
import com.example.cosmocatsapi.featuretoggle.FeatureToggles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@ExtendWith(FeatureToggleExtension.class)
@SpringBootTest
public class KittyProductControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldGet404KittyProductDisabled() throws Exception {
        mockMvc.perform(get("/api/v1/kitty-products")).andExpect(status().isNotFound());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldGet200KittyProductEnabled() throws Exception {
        mockMvc.perform(get("/api/v1/kitty-products")).andExpect(status().isOk());
    }
}