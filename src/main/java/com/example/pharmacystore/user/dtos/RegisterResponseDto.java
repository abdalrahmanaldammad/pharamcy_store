package com.example.pharmacystore.user.dtos;

import com.example.pharmacystore.user.Role;

public record RegisterResponseDto(
    String name, String email, String password, String imageUrl, Role role) {}
