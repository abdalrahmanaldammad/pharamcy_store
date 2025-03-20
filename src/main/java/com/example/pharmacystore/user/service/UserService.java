package com.example.pharmacystore.user.service;

import com.example.pharmacystore.exceptions.DuplicateResourceException;
import com.example.pharmacystore.exceptions.ImageUploadingException;
import com.example.pharmacystore.exceptions.VerificationException;
import com.example.pharmacystore.media.ImageResponseDto;
import com.example.pharmacystore.media.ImageService;
import com.example.pharmacystore.user.dtos.RegisterRequestDto;
import com.example.pharmacystore.user.dtos.RegisterResponseDto;
import com.example.pharmacystore.user.dtos.UpdateUserDto;
import com.example.pharmacystore.user.model.UserMoldel;
import com.example.pharmacystore.user.model.VerificationTokenModel;
import com.example.pharmacystore.user.repository.UserRepository;
import com.example.pharmacystore.user.repository.VerificationTokenRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final VerificationTokenRepository verificationTokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final ImageService imageService;
  private final EmailService emailService;

  public RegisterResponseDto registerUser(
      RegisterRequestDto registerRequestDto, MultipartFile image) throws MessagingException {

    Optional<UserMoldel> byEmail = userRepository.findByEmail(registerRequestDto.getEmail());

    if (byEmail.isPresent()) {
      throw new DuplicateResourceException("Email already exists");
    }

    String imageUrl = "";
    try {
      if (image != null) {
        ImageResponseDto imageResponseDto = imageService.uploadImage(image);
        imageUrl = imageResponseDto.getUrl();
      }

    } catch (Exception e) {
      throw new ImageUploadingException(e.getMessage());
    }
    UserMoldel user = new UserMoldel();
    user.setName(registerRequestDto.getName());
    user.setEmail(registerRequestDto.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
    user.setImageUrl(imageUrl == "" ? null : imageUrl);
    user.setRole(registerRequestDto.getRole());
    userRepository.save(user);

    String code = emailService.generateVerificationCode();
    VerificationTokenModel verificationTokenModel = new VerificationTokenModel();
    verificationTokenModel.setToken(code);
    verificationTokenModel.setUser(user);
    verificationTokenRepository.save(verificationTokenModel);

    emailService.sendVerificationEmail(user.getEmail(), code);

    return new RegisterResponseDto(
        user.getName(), user.getEmail(), user.getPassword(), user.getImageUrl(), user.getRole());
  }

  @Transactional
  public String verifyAccount(String mail, String code) {
    Optional<VerificationTokenModel> verificationTokenModel =
        verificationTokenRepository.findByToken(code);
    if (verificationTokenModel.isEmpty()) {
      throw new VerificationException("Invalid verification request");
    }
    VerificationTokenModel verificationToken = verificationTokenModel.get();

    if (!verificationToken.getToken().equals(code)) {
      throw new VerificationException("Invalid verification code");
    }

    if (verificationToken.getExpiryDate().before(new Date())) {
      throw new VerificationException("verification code expired");
    }
    UserMoldel user = verificationToken.getUser();
    user.setVerified(true);
    userRepository.save(user);

    verificationTokenRepository.deleteById(verificationToken.getId());

    return "Email verified";
  }

  public RegisterResponseDto updateUser(Long id, UpdateUserDto updateUserDto, MultipartFile image)
      throws MessagingException {
    Optional<UserMoldel> byId = userRepository.findById(id);
    if (byId.isEmpty()) {
      throw new UsernameNotFoundException("user is not found");
    }

    UserMoldel user = byId.get();
    if (updateUserDto.getName() != null) {
      user.setName(updateUserDto.getName());
    }
    if (updateUserDto.getEmail() != null) {
      user.setEmail(updateUserDto.getEmail());
    }
    if (updateUserDto.getRole() != null) {
      user.setRole(updateUserDto.getRole());
    }
    if (image != null) {
      try {
        ImageResponseDto imageResponseDto = imageService.uploadImage(image);
        String imageUrl = imageResponseDto.getUrl();
        user.setImageUrl(imageUrl);
      } catch (Exception e) {
        throw new ImageUploadingException(e.getMessage());
      }
    }
    userRepository.save(user);
    return new RegisterResponseDto(
        user.getName(), user.getEmail(), user.getPassword(), user.getImageUrl(), user.getRole());
  }
}
