package com.example.pharmacystore.user;

import com.example.pharmacystore.user.dtos.RegisterRequestDto;
import com.example.pharmacystore.user.dtos.RegisterResponseDto;
import com.example.pharmacystore.user.dtos.UpdateUserDto;
import com.example.pharmacystore.user.model.UserMoldel;
import com.example.pharmacystore.user.repository.UserRepository;
import com.example.pharmacystore.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

// @RestController("/user")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

  public final UserService userService;
  private final UserRepository userRepository;

  @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<RegisterResponseDto> register(
      @RequestPart("user") String userJson, // Accept JSON as a raw string
      @RequestPart(value = "file", required = false) MultipartFile file) {

    try {
      // Convert JSON string to RegisterRequestDto object
      ObjectMapper objectMapper = new ObjectMapper();
      RegisterRequestDto registerRequestDto =
          objectMapper.readValue(userJson, RegisterRequestDto.class);

      // Process user registration
      RegisterResponseDto responseDto = userService.registerUser(registerRequestDto, file);
      return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @PostMapping("/verify")
  public ResponseEntity<?> verifyUser(@RequestParam String token, @RequestParam String email) {
    String res = userService.verifyAccount(email, token);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @PutMapping("/update-User")
  public ResponseEntity<RegisterResponseDto> updateUser(
      @PathVariable Long id,
      @Valid @RequestPart("user") String userJson,
      @RequestPart(value = "file", required = false) MultipartFile file) {

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      UpdateUserDto updateUserDto = objectMapper.readValue(userJson, UpdateUserDto.class);
      RegisterResponseDto responseDto = userService.updateUser(id, updateUserDto, file);
      return new ResponseEntity<>(responseDto, HttpStatus.OK);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @PostMapping("/abood")
  public ResponseEntity<String> abood() {
    return new ResponseEntity<>("abood", HttpStatus.OK);
  }

  @GetMapping("/apilogin")
  public ResponseEntity<RegisterResponseDto> login(Authentication authentication) {
    UserMoldel userMoldel =
        userRepository
            .findByEmail(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException("the user is not found"));

    return new ResponseEntity<>(
        new RegisterResponseDto(
            userMoldel.getName(),
            userMoldel.getEmail(),
            userMoldel.getPassword(),
            userMoldel.getImageUrl(),
            userMoldel.getRole()),
        HttpStatus.OK);
  }
}
