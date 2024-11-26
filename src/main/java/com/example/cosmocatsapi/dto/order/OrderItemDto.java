package com.example.cosmocatsapi.dto.order;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Value;

@Value
public class OrderItemDto {
    @NotNull(message = "Product name cannot be null")
    String product;
    @NotNull(message = "Product price cannot be null")
    BigDecimal price;
    @NotNull(message = "Product quantity cannot be null")
    Integer quantity;
}
