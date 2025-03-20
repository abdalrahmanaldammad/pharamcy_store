package com.example.pharmacystore.config;

import com.example.pharmacystore.exceptions.CustomAccessDeniedHandler;
import com.example.pharmacystore.filters.CsrfCookieFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler =
        new CsrfTokenRequestAttributeHandler();
    return http.securityContext(contextConfig -> contextConfig.requireExplicitSave(false))
        .sessionManagement(
            sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
        .cors(
            corsConfig ->
                corsConfig.configurationSource(
                    new CorsConfigurationSource() {
                      @Override
                      public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowCredentials(true);
                        config.setAllowedOrigins(
                            Collections.singletonList("http://localhost:3000"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                      }
                    }))
        .csrf(
            csrfConfig ->
                csrfConfig
                    .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                    .ignoringRequestMatchers("/user/register", "/verify")
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
        .authorizeHttpRequests(
            request ->
                request
                    .requestMatchers("/user/register", "user/verify")
                    .permitAll()
                    .requestMatchers(
                        "/order/my-order",
                        "/order/creat",
                        "/order/update/{id}",
                        "order/cancel/",
                        "favorite/*")
                    .hasRole("USER")
                    .requestMatchers("/drug/*", "/category/*", "order/update-status/{id}")
                    .hasRole("ADMIN")
                    .requestMatchers("/user/apilogin")
                    .authenticated())
        .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable())
        .httpBasic(Customizer.withDefaults())
        .exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()))
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
