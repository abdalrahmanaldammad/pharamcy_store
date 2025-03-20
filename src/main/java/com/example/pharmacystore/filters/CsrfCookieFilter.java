package com.example.pharmacystore.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CsrfCookieFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
          HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {

    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

    if (csrfToken != null) {
      String token = csrfToken.getToken();

      // ✅ Force the browser to set the CSRF token in a cookie
      response.addHeader("Set-Cookie", "XSRF-TOKEN=" + token + "; Path=/; Secure; HttpOnly=false; SameSite=Lax");
    }

    filterChain.doFilter(request, response);
  }
}
