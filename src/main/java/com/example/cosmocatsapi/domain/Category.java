package com.example.cosmocatsapi.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Category {
    long id;
    String name;
    String description;
}
