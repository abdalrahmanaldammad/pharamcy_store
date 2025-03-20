package com.example.pharmacystore.drug.controller;

import com.example.pharmacystore.drug.dtos.DrugDto;
import com.example.pharmacystore.drug.dtos.UpdateDrugDto;
import com.example.pharmacystore.drug.service.DrugService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drug")
@AllArgsConstructor
public class DrugController {

  private DrugService drugService;

  @PostMapping("/creat")
  public ResponseEntity<DrugDto> creatDrug(@Valid @RequestBody DrugDto drugDto) {
    DrugDto response = drugService.creatDrug(drugDto);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<DrugDto> updateDrug(
      @Validated({Default.class}) @RequestBody UpdateDrugDto drugDto, @PathVariable Long id) {
    DrugDto response = drugService.updateDrug(drugDto, id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/detail/{id}")
  public ResponseEntity<DrugDto> getDrugDetail(@PathVariable Long id) {
    DrugDto drugDetial = drugService.getDrugDetial(id);
    return ResponseEntity.ok(drugDetial);
  }

  @GetMapping("/search")
  public ResponseEntity<Page<DrugDto>> searchDrug(
      @RequestParam(required = false) String commercialName,
      @RequestParam(required = false) String categoryName,
      @RequestParam(required = false) String manufacutrer,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    Page<DrugDto> resutl =
        drugService.searchProducts(
            commercialName, categoryName, manufacutrer, sortBy, sortDir, page, size);
    return ResponseEntity.ok(resutl);
  }


}
