package com.example.pharmacystore.favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteModel, Long> {
  List<FavoriteModel> findByUserId(Long id);
}
