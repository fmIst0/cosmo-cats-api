package com.example.cosmocatsapi.service;

import com.example.cosmocatsapi.dto.product.ProductRequestDto;
import com.example.cosmocatsapi.dto.product.ProductResponseDto;
import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto getProductById(Long id);
    ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto);
    void deleteProduct(Long id);
}
