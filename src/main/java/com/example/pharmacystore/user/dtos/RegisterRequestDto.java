package com.example.pharmacystore.user.dtos;

import com.example.pharmacystore.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RegisterRequestDto {

  @NotEmpty private String name;

  @Email @NotEmpty private String email;

  @NotEmpty
  @Size(min = 6)
  private String password;

  private Role role = Role.USER;
}
