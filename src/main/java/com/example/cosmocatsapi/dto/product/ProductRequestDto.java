package com.example.cosmocatsapi.dto.product;

import com.example.cosmocatsapi.common.ProductStatus;
import com.example.cosmocatsapi.dto.validation.CosmicWordCheck;
import com.example.cosmocatsapi.dto.validation.Group;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductRequestDto {
    @NotBlank(message = "Product name is mandatory")
    @Size(max = 100, message = "Product name cannot exceed 100 characters")
    @CosmicWordCheck(groups = Group.class)
    String name;
    @NotBlank(message = "Product description is mandatory")
    @Size(max = 1000, message = "Product description cannot exceed 1000 cahracters")
    String description;
    @NotNull(message = "Product price is mandatory")
    BigDecimal price;
    @NotNull(message = "Product category is mandatory")
    String categoryId;
    ProductStatus productStatus;
}
