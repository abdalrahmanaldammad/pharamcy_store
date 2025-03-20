package com.example.pharmacystore.user.repository;

import com.example.pharmacystore.user.model.VerificationTokenModel;
import org.hibernate.sql.Delete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenModel, Long> {
  Optional<VerificationTokenModel> findByToken(String token);

  @Transactional
  @Modifying
  @Query("delete from  VerificationTokenModel v where v.id=:id")
  void deleteById(@Param("id") Long id);
}
