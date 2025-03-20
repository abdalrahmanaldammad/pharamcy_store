package com.example.pharmacystore.drug.service;

import com.example.pharmacystore.drug.dtos.DrugDto;
import com.example.pharmacystore.drug.dtos.UpdateDrugDto;
import com.example.pharmacystore.drug.model.CategoryModel;
import com.example.pharmacystore.drug.model.DrugModel;
import com.example.pharmacystore.drug.repository.CategoryRepository;
import com.example.pharmacystore.drug.repository.DrugRepository;
import com.example.pharmacystore.exceptions.CategoryNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DrugService {

  private DrugRepository drugRepository;
  private CategoryRepository categoryRepository;

  public DrugDto creatDrug(DrugDto drugDto) {
    DrugModel drugModel = new DrugModel();
    drugModel.setCommercialName(drugDto.getCommercialName());
    drugModel.setPrice(drugDto.getPrice());
    drugModel.setManufacturer(drugDto.getManufacturer());
    drugModel.setAvailableQuantity(drugDto.getAvailableQuantity());
    drugModel.setExpirationDate(drugDto.getExpirationDate());

    CategoryModel categoryModel =
        categoryRepository
            .findByCategoryName(drugDto.getCategoryName())
            .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

    drugModel.setCategory(categoryModel);

    drugRepository.save(drugModel);

    return new DrugDto(
        drugModel.getId(),
        drugModel.getCommercialName(),
        drugModel.getAvailableQuantity(),
        drugModel.getManufacturer(),
        drugModel.getExpirationDate(),
        drugModel.getPrice(),
        drugModel.getCategory().getCategoryName());
  }

  public DrugDto updateDrug(UpdateDrugDto updateDrugDto, Long id) {
    Optional<DrugModel> byId = drugRepository.findById(id);
    if (byId.isEmpty()) {
      return null;
    }
    DrugModel drugModel = byId.get();
    if (updateDrugDto.getCommercialName() != null && !updateDrugDto.getCommercialName().isEmpty()) {
      drugModel.setCommercialName(updateDrugDto.getCommercialName());
    }
    if (updateDrugDto.getPrice() != null) {
      drugModel.setPrice(updateDrugDto.getPrice());
    }
    if (updateDrugDto.getAvailableQuantity() != null) {
      drugModel.setAvailableQuantity(drugModel.getAvailableQuantity());
    }
    if (updateDrugDto.getManufacturer() != null) {
      drugModel.setManufacturer(updateDrugDto.getManufacturer());
    }
    if (updateDrugDto.getCategoryName() != null) {
      CategoryModel categoryModel =
          categoryRepository
              .findByCategoryName(updateDrugDto.getCategoryName())
              .orElseThrow(() -> new CategoryNotFoundException("the category is not found"));
      drugModel.setCategory(categoryModel);
    }
    if (updateDrugDto.getExpirationDate() != null) {
      drugModel.setExpirationDate(updateDrugDto.getExpirationDate());
    }
    drugRepository.save(drugModel);
    return new DrugDto(
        drugModel.getId(),
        drugModel.getCommercialName(),
        drugModel.getAvailableQuantity(),
        drugModel.getManufacturer(),
        drugModel.getExpirationDate(),
        drugModel.getPrice(),
        drugModel.getCategory().getCategoryName());
  }

  public DrugDto getDrugDetial(Long id) {
    Optional<DrugModel> byId = drugRepository.findById(id);
    if (byId.isEmpty()) {
      return null;
    }
    DrugModel drugModel = byId.get();
    return new DrugDto(
        drugModel.getId(),
        drugModel.getCommercialName(),
        drugModel.getAvailableQuantity(),
        drugModel.getManufacturer(),
        drugModel.getExpirationDate(),
        drugModel.getPrice(),
        drugModel.getCategory().getCategoryName());
  }

  public Page<DrugDto> searchProducts(
      String commercialName,
      String categoryName,
      String manufacturerName,
      String sortBy,
      String sortDir,
      int page,
      int size) {
    Pageable pageable =
        PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
    Specification<DrugModel> specification =
        DrugSpecification.filterBy(commercialName, categoryName, manufacturerName);
    Page<DrugModel> all = drugRepository.findAll(specification, pageable);
    return all.map(
        drugModel ->
            new DrugDto(
                drugModel.getId(),
                drugModel.getCommercialName(),
                drugModel.getAvailableQuantity(),
                drugModel.getManufacturer(),
                drugModel.getExpirationDate(),
                drugModel.getPrice(),
                drugModel.getCategory().getCategoryName()));
  }
}
