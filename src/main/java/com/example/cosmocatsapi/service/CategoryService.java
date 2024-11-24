package com.example.cosmocatsapi.service;

import com.example.cosmocatsapi.dto.category.CategoryDto;
import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto);
    CategoryDto getCategoryById(int id);
    List<CategoryDto> getAllCategories();
    void deleteCategoryById(int id);
}
