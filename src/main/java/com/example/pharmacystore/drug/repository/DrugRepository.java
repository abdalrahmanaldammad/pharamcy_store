package com.example.pharmacystore.drug.repository;

import com.example.pharmacystore.drug.model.DrugModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository
    extends JpaRepository<DrugModel, Long>, JpaSpecificationExecutor<DrugModel> {}
