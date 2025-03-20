package com.example.pharmacystore.drug.repository;

import com.example.pharmacystore.drug.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
  Optional<CategoryModel> findByCategoryName(String categoryName);
}
