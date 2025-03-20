package com.example.pharmacystore.favorite;

import com.example.pharmacystore.drug.model.DrugModel;
import com.example.pharmacystore.drug.repository.DrugRepository;
import com.example.pharmacystore.exceptions.DrugNotFoundException;
import com.example.pharmacystore.user.model.UserMoldel;
import com.example.pharmacystore.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoriteService {
  private FavoriteRepository favoriteRepository;
  private DrugRepository drugRepository;
  private UserRepository userRepository;

  public FavoriteModel addFavorite(Long drug_id) {
    FavoriteModel favorite = new FavoriteModel();

    String email = getMyEmail();

    UserMoldel userMoldel =
        userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

    DrugModel drugModel =
        drugRepository
            .findById(drug_id)
            .orElseThrow(() -> new DrugNotFoundException("Drug's not found"));

    favorite.setUser(userMoldel);
    favorite.setDrug(drugModel);

    favoriteRepository.save(favorite);
    return favorite;
  }

  private static String getMyEmail() {
    UserDetails userDetails =
        (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    String email = userDetails.getUsername();
    return email;
  }

  public List<FavoriteModel> findAllFavorites() {
    String myEmail = getMyEmail();

    UserMoldel userMoldel =
        userRepository
            .findByEmail(myEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

    List<FavoriteModel> byUserId = favoriteRepository.findByUserId(userMoldel.getId());
    return byUserId;
  }
}
