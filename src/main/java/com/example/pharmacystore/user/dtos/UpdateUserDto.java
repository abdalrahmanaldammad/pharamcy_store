package com.example.pharmacystore.user.dtos;

import com.example.pharmacystore.user.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateUserDto {
  private String name;

  @Email private String email;
  private Role role;
}
