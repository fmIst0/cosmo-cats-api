package com.example.cosmocatsapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.cosmocatsapi.common.ProductStatus;
import com.example.cosmocatsapi.domain.Product;
import com.example.cosmocatsapi.dto.product.ProductRequestDto;
import com.example.cosmocatsapi.dto.product.ProductResponseDto;
import com.example.cosmocatsapi.service.exception.ProductNotFoundException;
import com.example.cosmocatsapi.service.mappers.ProductMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Verify createProduct() method works")
    public void createProduct_ValidProductRequestDto_ReturnsProductResponseDto() {
        //Given
        ProductRequestDto productRequestDto = getRequestProductDto();

        ProductResponseDto productResponseDto = getProductResponseDto();

        when(productMapper.toProductResponseDto(any())).thenReturn(productResponseDto);

        //When
        ProductResponseDto savedProduct = productService.createProduct(productRequestDto);

        //Then
        assertThat(savedProduct).isEqualTo(productResponseDto);
        verify(productMapper, times(1)).toProductResponseDto(any());
        verifyNoMoreInteractions(productMapper);

    }

    @Test
    @DisplayName("Verify getAllProducts() returns a list of products")
    public void getAllProducts_ShouldReturnProductList() {
        // Given
        Product product1 = Product.builder().id(1).name("Product 1").description("Description 1")
                .price(BigDecimal.valueOf(5)).productStatus(ProductStatus.IN_STOCK).categoryId(1).build();
        Product product2 = Product.builder().id(2).name("Product 2").description("Description 2")
                .price(BigDecimal.valueOf(10)).productStatus(ProductStatus.DISCONTINUED).categoryId(2).build();

        ProductResponseDto responseDto1 = ProductResponseDto.builder().id(1).name("Product 1")
                .description("Description 1").price(BigDecimal.valueOf(5)).productStatus(ProductStatus.IN_STOCK).categoryId(1).build();
        ProductResponseDto responseDto2 = ProductResponseDto.builder().id(2).name("Product 2")
                .description("Description 2").price(BigDecimal.valueOf(10)).productStatus(ProductStatus.DISCONTINUED).categoryId(2).build();

        when(productMapper.toProductResponseDto(product1)).thenReturn(responseDto1);
        when(productMapper.toProductResponseDto(product2)).thenReturn(responseDto2);

        // When
        List<ProductResponseDto> result = productService.getAllProducts();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Product 1", result.get(0).getName());
        assertEquals("Product 2", result.get(1).getName());
        verify(productMapper, times(3)).toProductResponseDto(any(Product.class));
    }

    @Test
    @DisplayName("Verify getProductById() returns the correct product")
    public void getProductById_ValidId_ShouldReturnProduct() {
        // Given
        Long productId = 1L;
        Product product = Product.builder().id(1).name("Product 1").description("Description 1")
                .price(BigDecimal.valueOf(5)).productStatus(ProductStatus.IN_STOCK).categoryId(1).build();
        ProductResponseDto responseDto = ProductResponseDto.builder().id(1).name("Product 1")
                .description("Description 1").price(BigDecimal.valueOf(5)).productStatus(ProductStatus.IN_STOCK).categoryId(1).build();

        when(productMapper.toProductResponseDto(product)).thenReturn(responseDto);

        // When
        ProductResponseDto result = productService.getProductById(productId);

        // Then
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Product 1", result.getName());
        verify(productMapper, times(1)).toProductResponseDto(product);
    }

    @Test
    @DisplayName("Verify getProductById() throws exception when product is not found")
    public void getProductById_InvalidId_ShouldThrowException() {
        // Given
        Long invalidProductId = 999L;

        // When & Then
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(invalidProductId));
    }

    @Test
    @DisplayName("Verify updateProduct() updates and returns the product")
    public void updateProduct_ValidIdAndRequest_ShouldReturnUpdatedProduct() {
        // Given
        Long productId = 1L;
        ProductRequestDto requestDto = ProductRequestDto.builder().name("Updated Name")
                .description("Updated Description").price(BigDecimal.valueOf(15))
                .productStatus(ProductStatus.DISCONTINUED).categoryId(2).build();
        Product productToUpdate = Product.builder().name("Updated Name")
                .description("Updated Description").price(BigDecimal.valueOf(15))
                .productStatus(ProductStatus.DISCONTINUED).categoryId(2).build();
        ProductResponseDto responseDto = ProductResponseDto.builder().id(1).name("Updated Name")
                .description("Updated Description").price(BigDecimal.valueOf(15))
                .productStatus(ProductStatus.DISCONTINUED).categoryId(2).build();

        when(productMapper.toProductResponseDto(any()))
                .thenReturn(responseDto);

        // When
        ProductResponseDto result = productService.updateProduct(productId, requestDto);

        // Then
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals(BigDecimal.valueOf(15), result.getPrice());
        assertEquals(ProductStatus.DISCONTINUED, result.getProductStatus());
        verify(productMapper, times(1)).toProductResponseDto(any());
    }

    @Test
    @DisplayName("Verify deleteProduct() removes the product")
    public void deleteProduct_ValidId_ShouldRemoveProduct() {
        // Given
        Long productId = 1L;

        // When
        productService.deleteProduct(productId);

        // Then
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
    }


    private ProductRequestDto getRequestProductDto() {
        return ProductRequestDto.builder()
                .name("Test")
                .description("Test")
                .price(BigDecimal.valueOf(5))
                .productStatus(ProductStatus.IN_STOCK)
                .categoryId(1)
                .build();
    }

    private ProductResponseDto getProductResponseDto() {
        return ProductResponseDto.builder().id(9999999)
                .name("Test")
                .description("Test")
                .price(BigDecimal.valueOf(5))
                .productStatus(ProductStatus.IN_STOCK)
                .categoryId(1)
                .build();
    }
}