package com.example.cosmocatsapi.dto.product;

import com.example.cosmocatsapi.common.ProductStatus;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductResponseDto {
    long id;
    String name;
    String description;
    BigDecimal price;
    ProductStatus productStatus;
    long categoryId;
}
