package com.example.townmarket.common.oauth;


import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.enums.RoleEnum;
import com.example.townmarket.common.jwtUtil.JwtUtil;
import com.example.townmarket.common.domain.user.repository.UserRepository;
import com.example.townmarket.common.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
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
//       프론트 엔드에 전달할 응답 생성
//      Map<Object, Object> data = new HashMap<>();
//
//      data.put("username", username);
//      data.put("accessToken", accessToken);
//      data.put("refreshToken", refreshToken);

      response.setContentType("application/json");
//      ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(model);
//      new ObjectMapper().writeValue(response.getOutputStream(), data);
      String redirectUrl = "http://localhost:5500/index.html";

      response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
      response.addHeader(JwtUtil.REFRESH_HEADER, refreshToken);
      response.sendRedirect(redirectUrl);

//      response.sendRedirect("http://localhost:5500/index.html");
    }
  }
}
