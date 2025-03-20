package com.example.pharmacystore.user.repository;

import com.example.pharmacystore.user.model.UserMoldel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserMoldel, Long> {
  Optional<UserMoldel> findByEmail(String email);
}
