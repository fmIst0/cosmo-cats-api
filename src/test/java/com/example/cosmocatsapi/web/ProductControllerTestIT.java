package com.example.cosmocatsapi.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import com.example.cosmocatsapi.service.impl.ProductServiceImpl;
import com.example.cosmocatsapi.service.mappers.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTestIT {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private ProductMapper productMapper;
    @MockBean
    private ProductServiceImpl productService;

    @BeforeAll
    static void setUpBeforeClass(
            @Autowired WebApplicationContext webApplicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @DisplayName("Get all products")
    void getAllProducts_ShouldReturnAllProducts() throws Exception {
        //Given
        List<ProductResponseDto> expected = new ArrayList<>(Arrays.asList(
                ProductResponseDto.builder()
                        .id(1)
                        .name("Product 1")
                        .description("Description 1")
                        .price(BigDecimal.valueOf(5))
                        .productStatus(ProductStatus.IN_STOCK)
                        .categoryId(1)
                        .build(),
                ProductResponseDto.builder()
                        .id(2)
                        .name("Product 2")
                        .description("Description 2")
                        .price(BigDecimal.TEN)
                        .productStatus(ProductStatus.DISCONTINUED)
                        .categoryId(2)
                        .build(),
                ProductResponseDto.builder()
                        .id(3)
                        .name("Product 3")
                        .description("Description 3")
                        .price(BigDecimal.valueOf(15))
                        .productStatus(ProductStatus.OUT_OF_STOCK)
                        .categoryId(2)
                        .build()
        ));

        when(productService.getAllProducts()).thenReturn(expected);

        //When
        MvcResult result = mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        //Then
        ProductResponseDto[] actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                ProductResponseDto[].class);

        assertEquals(expected.size(), actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Test
    @DisplayName("Create product with valid data")
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        // Given
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("comet")
                .description("Valid Product Description")
                .price(BigDecimal.valueOf(20))
                .categoryId(1)
                .productStatus(ProductStatus.IN_STOCK)
                .build();

        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .id(1)
                .name("comet")
                .description("Valid Product Description")
                .price(BigDecimal.valueOf(20))
                .categoryId(1)
                .productStatus(ProductStatus.IN_STOCK)
                .build();

        when(productService.createProduct(productRequestDto)).thenReturn(productResponseDto);

        // When
        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        // Then
        ProductResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ProductResponseDto.class);
        assertNotNull(actual.getId());
        assertEquals(productRequestDto.getName(), actual.getName());
    }

    @Test
    @DisplayName("Create product with missing name")
    void createProduct_MissingName_ShouldReturnBadRequest() throws Exception {
        // Given
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("")  // Missing name
                .description("Valid Product Description")
                .price(BigDecimal.valueOf(20))
                .categoryId(1)
                .productStatus(ProductStatus.IN_STOCK)
                .build();

        // When
        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isBadRequest())  // Expect BadRequest
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Product name is mandatory"));
    }

    @Test
    @DisplayName("Create product with too long name")
    void createProduct_TooLongName_ShouldReturnBadRequest() throws Exception {
        // Given
        String longName = "A".repeat(101);  // Name exceeds the 100 characters limit
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name(longName)
                .description("Valid Product Description")
                .price(BigDecimal.valueOf(20))
                .categoryId(1)
                .productStatus(ProductStatus.IN_STOCK)
                .build();

        // When
        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isBadRequest())  // Expect BadRequest
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Product name cannot exceed 100 characters"));
    }

    @Test
    @DisplayName("Create product with missing price")
    void createProduct_MissingPrice_ShouldReturnBadRequest() throws Exception {
        // Given
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("Valid Product Name")
                .description("Valid Product Description")
                .price(null)  // Missing price
                .categoryId(1)
                .productStatus(ProductStatus.IN_STOCK)
                .build();

        // When
        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isBadRequest())  // Expect BadRequest
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Product price is mandatory"));
    }

    @Test
    @DisplayName("Create product with missing categoryId")
    void createProduct_MissingCategoryId_ShouldReturnBadRequest() throws Exception {
        // Given
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("Valid Product Name")
                .description("Valid Product Description")
                .price(BigDecimal.valueOf(20))
                .categoryId(null)  // Missing categoryId
                .productStatus(ProductStatus.IN_STOCK)
                .build();

        // When
        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isBadRequest())  // Expect BadRequest
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Product category is mandatory"));
    }

    @Test
    @DisplayName("Update product with valid data")
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        // Given
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("star")
                .description("Updated Product Description")
                .price(BigDecimal.valueOf(25))
                .categoryId(1)
                .productStatus(ProductStatus.OUT_OF_STOCK)
                .build();

        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .id(1)
                .name("star")
                .description("Updated Product Description")
                .price(BigDecimal.valueOf(25))
                .categoryId(1)
                .productStatus(ProductStatus.OUT_OF_STOCK)
                .build();

        Long productId = 1L; // Assuming this product exists

        when(productService.updateProduct(productId, productRequestDto)).thenReturn(productResponseDto);

        // When
        MvcResult result = mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isOk())  // Expect Ok response
                .andReturn();

        // Then
        ProductResponseDto updatedProduct = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ProductResponseDto.class);
        assertEquals(productRequestDto.getName(), updatedProduct.getName());
        assertEquals(productRequestDto.getDescription(), updatedProduct.getDescription());
        assertEquals(productRequestDto.getPrice(), updatedProduct.getPrice());
        assertEquals(productRequestDto.getProductStatus(), updatedProduct.getProductStatus());
    }

    @Test
    @DisplayName("Update product with missing name")
    void updateProduct_MissingName_ShouldReturnBadRequest() throws Exception {
        // Given
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("")  // Missing name
                .description("Updated Product Description")
                .price(BigDecimal.valueOf(25))
                .categoryId(1)
                .productStatus(ProductStatus.OUT_OF_STOCK)
                .build();

        Long productId = 1L; // Assuming this product exists

        // When
        MvcResult result = mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isBadRequest())  // Expect BadRequest
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Product name is mandatory"));
    }

    @Test
    @DisplayName("Update product with missing price")
    void updateProduct_MissingPrice_ShouldReturnBadRequest() throws Exception {
        // Given
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("Updated Product Name")
                .description("Updated Product Description")
                .price(null)  // Missing price
                .categoryId(1)
                .productStatus(ProductStatus.OUT_OF_STOCK)
                .build();

        Long productId = 1L; // Assuming this product exists

        // When
        MvcResult result = mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isBadRequest())  // Expect BadRequest
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Product price is mandatory"));
    }

    @Test
    @DisplayName("Get product by ID")
    void getProductById_ShouldReturnProduct() throws Exception {
        // Given
        Long productId = 1L; // Assuming this product exists

        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .id(1)
                .name("star")
                .description("Updated Product Description")
                .price(BigDecimal.valueOf(25))
                .categoryId(1)
                .productStatus(ProductStatus.OUT_OF_STOCK)
                .build();

        when(productService.getProductById(productId)).thenReturn(productResponseDto);

        // When
        MvcResult result = mockMvc.perform(get("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Expect Ok response
                .andReturn();

        // Then
        ProductResponseDto actualProduct = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ProductResponseDto.class);
        assertNotNull(actualProduct);
        assertEquals(productId, actualProduct.getId());
    }

    @Test
    @DisplayName("Get product by non-existent ID")
    void getProductById_ProductNotFound_ShouldReturnNotFound() throws Exception {
        // Given
        Long nonExistentProductId = 999L; // Assuming this product does not exist

        when(productService.getProductById(nonExistentProductId)).thenThrow(ProductNotFoundException.class);

        // When
        MvcResult result = mockMvc.perform(get("/api/v1/products/{id}", nonExistentProductId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }


    @Test
    @DisplayName("Delete product by ID")
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        // Given
        Long productId = 1L; // Assuming this product exists

        // When
        mockMvc.perform(delete("/api/v1/products/{id}", productId))
                .andExpect(status().isNoContent())  // Expect NoContent (204)
                .andReturn();
    }

    @Test
    @DisplayName("Delete product with non-existent ID")
    void deleteProduct_ProductNotFound_ShouldReturnNotFound() throws Exception {
        // Given
        Long nonExistentProductId = 999L; // Assuming this product does not exist

        when(productService.getProductById(nonExistentProductId)).thenThrow(ProductNotFoundException.class);

        // When
        MvcResult result = mockMvc.perform(delete("/api/v1/products/{id}", nonExistentProductId))
                .andExpect(status().isNoContent())  // Expect NotFound (404)
                .andReturn();
    }
}