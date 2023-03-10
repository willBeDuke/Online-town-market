package com.example.townmarket.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private static final SecurityExceptionDto exceptionDto =
      new SecurityExceptionDto(HttpStatus.FORBIDDEN.value(), "접근이 거부되었습니다. 권한이 없습니다");

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      org.springframework.security.access.AccessDeniedException accessDeniedException)
      throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.FORBIDDEN.value());

    try (OutputStream os = response.getOutputStream()) {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writeValue(os, exceptionDto);
      os.flush();
    }
  }
}
