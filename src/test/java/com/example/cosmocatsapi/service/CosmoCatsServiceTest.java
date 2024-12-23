package com.example.cosmocatsapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.cosmocatsapi.domain.CosmoCat;
import com.example.cosmocatsapi.service.impl.CosmoCatsServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("Cosmo Cats Service Test")
@SpringBootTest(classes = {CosmoCatsServiceImpl.class})
public class CosmoCatsServiceTest {

    @Autowired
    @Spy
    private CosmoCatsService cosmoCatsService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetCosmoCats() {
        List<CosmoCat> cosmoCats = new ArrayList<>(List.of(
                CosmoCat.builder().id(1L).name("Test cosmo cat").countOfPaws(5).price(5.66).build()
        ));

        Mockito.when(cosmoCatsService.getCosmoCats()).thenReturn(cosmoCats);
        assertEquals(cosmoCats, cosmoCatsService.getCosmoCats());
    }
}