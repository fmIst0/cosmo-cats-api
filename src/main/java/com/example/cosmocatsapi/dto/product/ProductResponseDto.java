package com.example.cosmocatsapi.dto.product;

import com.example.cosmocatsapi.common.ProductStatus;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductResponseDto {
    String id;
    String name;
    String description;
    BigDecimal price;
    ProductStatus productStatus;
    String categoryId;
}
