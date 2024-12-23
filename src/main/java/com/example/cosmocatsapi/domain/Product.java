package com.example.cosmocatsapi.domain;

import com.example.cosmocatsapi.common.ProductStatus;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    String id;
    String name;
    String description;
    BigDecimal price;
    ProductStatus productStatus;
    String categoryId;
}
