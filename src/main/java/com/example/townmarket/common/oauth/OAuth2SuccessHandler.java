package com.example.townmarket.common.oauth;


import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.enums.RoleEnum;
import com.example.townmarket.common.jwtUtil.JwtUtil;
import com.example.townmarket.common.domain.user.repository.UserRepository;
import com.example.townmarket.common.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();

    User user = userDetails.getUser();

    String username = user.getUsername().toLowerCase();

    String email = user.getEmail();

    if (userRepository.existsByEmail(email)) {
      String refreshToken = jwtUtil.createRefreshToken(username, user.getRole());
      String accessToken = jwtUtil.createAccessToken(username, user.getRole());

      response.setContentType("application/json");
//      ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(model);
//      new ObjectMapper().writeValue(response.getOutputStream(), data);
      String redirectUrl = "https://www.knock-knock.shop/index.html";
      Cookie cookie1 = new Cookie("accessToken", accessToken.substring(7));
      Cookie cookie2 = new Cookie("refreshToken", refreshToken.substring(7));
      cookie1.setPath("/");
      cookie2.setPath("/");
      response.addCookie(cookie1);
      response.addCookie(cookie2);
      response.sendRedirect(redirectUrl);

//      response.sendRedirect("http://localhost:5500/index.html");
    }
  }
}
