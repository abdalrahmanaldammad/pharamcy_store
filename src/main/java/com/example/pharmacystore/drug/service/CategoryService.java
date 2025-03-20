package com.example.pharmacystore.drug.service;

import com.example.pharmacystore.drug.dtos.CategoryDto;
import com.example.pharmacystore.drug.model.CategoryModel;
import com.example.pharmacystore.drug.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

  private CategoryRepository categoryRepository;

  public CategoryDto creatCategory(CategoryDto categoryDto) {
    CategoryModel categoryModel = new CategoryModel();
    categoryModel.setCategoryName(categoryDto.getCategoryName());
    categoryRepository.save(categoryModel);
    return new CategoryDto(categoryModel.getCategoryName());
  }

  public List<CategoryDto> getAllCategories() {
    List<CategoryModel> categoryModels = categoryRepository.findAll();
    return categoryModels.stream()
        .map(categoryModel -> new CategoryDto(categoryModel.getCategoryName()))
        .toList();
  }
}
