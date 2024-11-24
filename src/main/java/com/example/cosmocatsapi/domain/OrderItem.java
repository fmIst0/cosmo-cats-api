package com.example.cosmocatsapi.domain;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderItem {
    String product;
    BigDecimal price;
    Integer quantity;
}
