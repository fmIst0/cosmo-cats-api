package com.example.cosmocatsapi.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDto {
    @NotBlank(message = "Category name is mandatory")
    @Size(min = 1, max = 100, message = "Category name is from 1 to 100 characters")
    String name;
    @NotBlank(message = "Category description is mandatory")
    @Size(max = 1000, message = "Catgory description cannot exceed 1000 characters")
    String description;
}
