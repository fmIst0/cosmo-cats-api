package com.example.cosmocatsapi.service.mappers;

import com.example.cosmocatsapi.config.MapperConfig;
import com.example.cosmocatsapi.domain.Product;
import com.example.cosmocatsapi.dto.product.ProductRequestDto;
import com.example.cosmocatsapi.dto.product.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    Product toProduct(ProductRequestDto productRequestDto);

    ProductResponseDto toProductResponseDto(Product product);
}
