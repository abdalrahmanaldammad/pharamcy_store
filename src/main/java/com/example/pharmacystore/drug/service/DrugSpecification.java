package com.example.pharmacystore.drug.service;

import com.example.pharmacystore.drug.model.DrugModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DrugSpecification {
  public static Specification<DrugModel> filterBy(
      String commercialName, String categoryName, String manufacturrer) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (commercialName != null && !commercialName.isEmpty()) {
        predicates.add(
            criteriaBuilder.like(root.get("commercialName"), "%" + commercialName + "%"));
      }
      if (categoryName != null && !categoryName.isEmpty()) {
        predicates.add(
            criteriaBuilder.like(
                root.get("category").get("categoryName"), "%" + categoryName + "%"));
      }
      if (manufacturrer != null && !manufacturrer.isEmpty()) {
        predicates.add(criteriaBuilder.equal(root.get("manufacturer"), manufacturrer));
      }
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
