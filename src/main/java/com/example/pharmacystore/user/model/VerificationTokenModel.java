package com.example.pharmacystore.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "varification_tokens")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class VerificationTokenModel {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(
      name = "verification_token_sequence",
      sequenceName = "verification_token_sequence",
      allocationSize = 1)
  private long id;

  @NonNull private String token;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  @NonNull
  private UserMoldel user;

  private Date expiryDate = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
}
