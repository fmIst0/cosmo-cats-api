package com.example.cosmocatsapi.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CosmoCat {
    private long id;
    private String name;
    private int countOfPaws;
    private double price;
}