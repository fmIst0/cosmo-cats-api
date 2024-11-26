package com.example.cosmocatsapi.service.impl;

import com.example.cosmocatsapi.common.ProductStatus;
import com.example.cosmocatsapi.domain.Product;
import com.example.cosmocatsapi.dto.product.ProductRequestDto;
import com.example.cosmocatsapi.service.exception.ProductNotFoundException;
import com.example.cosmocatsapi.service.mappers.ProductMapper;
import com.example.cosmocatsapi.dto.product.ProductResponseDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.cosmocatsapi.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final Random random = new Random();
    private final List<Product> products = new ArrayList<>(Arrays.asList(
            Product.builder()
                    .id(1)
                    .name("Product 1")
                    .description("Description 1")
                    .price(BigDecimal.valueOf(5))
                    .productStatus(ProductStatus.IN_STOCK)
                    .categoryId(1)
                    .build(),
            Product.builder()
                    .id(2)
                    .name("Product 2")
                    .description("Description 2")
                    .price(BigDecimal.TEN)
                    .productStatus(ProductStatus.DISCONTINUED)
                    .categoryId(2)
                    .build(),
            Product.builder()
                    .id(3)
                    .name("Product 3")
                    .description("Description 3")
                    .price(BigDecimal.valueOf(15))
                    .productStatus(ProductStatus.OUT_OF_STOCK)
                    .categoryId(2)
                    .build()
        ));

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product newProduct = Product.builder()
                .id(products.size() + Math.abs(random.nextLong()))
                .name(productRequestDto.getName())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .productStatus(ProductStatus.IN_STOCK)
                .categoryId(productRequestDto.getCategoryId())
                .build();
        products.add(newProduct);
        return productMapper.toProductResponseDto(newProduct);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return products.stream()
                .map(productMapper::toProductResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        return productMapper.toProductResponseDto(findProductById(id));
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        Product productToUpdate = findProductById(id);
        productToUpdate.setName(productRequestDto.getName());
        productToUpdate.setDescription(productRequestDto.getDescription());
        productToUpdate.setPrice(productRequestDto.getPrice());
        productToUpdate.setProductStatus(productRequestDto.getProductStatus());
        productToUpdate.setCategoryId(productRequestDto.getCategoryId());
        return productMapper.toProductResponseDto(productToUpdate);
    }

    @Override
    public void deleteProduct(Long id) {
        Product productToDelete = findProductById(id);
        products.remove(productToDelete);
    }

    private Product findProductById(Long id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with id - " + id + " not found")
                );
    }
}
