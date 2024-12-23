package com.example.cosmocatsapi.service;

import com.example.cosmocatsapi.domain.CosmoCat;
import java.util.List;

public interface CosmoCatsService {
    List<CosmoCat> getCosmoCats();
}