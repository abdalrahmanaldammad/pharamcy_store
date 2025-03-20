package com.example.pharmacystore.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    LocalDateTime currentTime = LocalDateTime.now();
    String message =
        accessDeniedException != null && accessDeniedException.getMessage() != null
            ? accessDeniedException.getMessage()
            : "Unauthorized";
    String path = request.getRequestURI();
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType("application/json;charset=UTF-8");
    response.setHeader("Access-Control-Allow-Origin", "Authorization failed");

    String jsonResponse =
        String.format(
            "{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", "
                + "\"path\": \"%s\"}",
            currentTime,
            HttpStatus.UNAUTHORIZED.value(),
            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
            message,
            path);

    response.getWriter().write(jsonResponse);
  }
}
