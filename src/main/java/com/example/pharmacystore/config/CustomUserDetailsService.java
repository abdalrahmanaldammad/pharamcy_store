package com.example.pharmacystore.config;

import com.example.pharmacystore.user.model.UserMoldel;
import com.example.pharmacystore.user.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserMoldel user =
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    List<GrantedAuthority> authorities =
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));

    return new User(user.getEmail(), user.getPassword(), authorities);
  }
}
