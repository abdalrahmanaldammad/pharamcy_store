package com.example.pharmacystore.favorite;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorit")
public class FavoriteController {

  FavoriteService favoriteService;
  FavoriteRepository favoriteRepository;

  @PostMapping("/add-favoirt/{drug_id}}")
  public ResponseEntity<FavoriteModel> addFavoirt(@PathVariable Long drug_id) {
    FavoriteModel favoriteModel = favoriteService.addFavorite(drug_id);
    return ResponseEntity.ok(favoriteModel);
  }

  @DeleteMapping("/remove-favorit/{id}")
  public ResponseEntity<Boolean> removeFavoirt(@PathVariable Long id) {
    favoriteRepository.deleteById(id);
    return ResponseEntity.ok(true);
  }

  @GetMapping("/get-all-my-favorit")
  public ResponseEntity<List<FavoriteModel>> getAllMyFavoirt() {
    List<FavoriteModel> allFavorites = favoriteService.findAllFavorites();
    return ResponseEntity.ok(allFavorites);
  }
}
