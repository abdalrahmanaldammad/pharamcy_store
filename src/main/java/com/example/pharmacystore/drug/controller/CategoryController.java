package com.example.pharmacystore.drug.controller;

import com.example.pharmacystore.drug.dtos.CategoryDto;
import com.example.pharmacystore.drug.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

  private CategoryService categoryService;

  @PostMapping("/creat")
  public ResponseEntity<CategoryDto> creatCategory(@RequestBody CategoryDto categoryDto) {
    CategoryDto category = categoryService.creatCategory(categoryDto);
    return ResponseEntity.ok(category);
  }

  @GetMapping("/get-all")
  public ResponseEntity<List<CategoryDto>> getAllCategory() {
    List<CategoryDto> allCategories = categoryService.getAllCategories();
    return new ResponseEntity<>(allCategories, HttpStatus.OK);
  }
}
