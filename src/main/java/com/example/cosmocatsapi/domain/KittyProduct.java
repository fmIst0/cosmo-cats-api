package com.example.cosmocatsapi.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KittyProduct {
    private long id;
    private String name;
    private String description;
    private Double price;
}