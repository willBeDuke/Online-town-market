package com.example.townmarket.common.security;

import com.example.townmarket.common.jwtUtil.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsService;
  private final AdminDetailsServiceImpl adminDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String token = jwtUtil.resolveAccessToken(request);

    if (token != null) {
      if (!jwtUtil.validateToken(token)) {
        jwtExceptionHandler(response, "Token Error", HttpStatus.UNAUTHORIZED.value());
        return;
      }
      if (jwtUtil.isAdminToken(token)) {
        Claims info = jwtUtil.getUserInfoFromToken(token);
        setAdminAuthentication(info.getSubject());
        return;
      }

      Claims info = jwtUtil.getUserInfoFromToken(token);
      String username = info.getSubject();

      setAuthentication(username);
    }
    filterChain.doFilter(request, response);
  }


  public Authentication createAuthentication(String username) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  public void setAuthentication(String username) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    Authentication authentication = this.createAuthentication(username);
    context.setAuthentication(authentication);

    SecurityContextHolder.setContext(context);
  }

  public Authentication createAdminAuthentication(String username) {
    UserDetails userDetails = adminDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  public void setAdminAuthentication(String username) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    Authentication authentication = this.createAdminAuthentication(username);
    context.setAuthentication(authentication);

    SecurityContextHolder.setContext(context);
  }

  public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
    response.setStatus(statusCode);
    response.setContentType("application/json");
    try {
      String json = new ObjectMapper().writeValueAsString(
          new SecurityExceptionDto(statusCode, msg));
      response.getWriter().write(json);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
