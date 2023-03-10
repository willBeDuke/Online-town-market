package com.example.townmarket.common.redis.controller;

import com.example.townmarket.common.dto.StatusResponse;
import com.example.townmarket.common.enums.ResponseMessages;
import com.example.townmarket.common.redis.service.RefreshService;
import com.example.townmarket.common.util.SetHttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/refresh")
public class RefreshController {

  private final RefreshService refreshService;

  // 토큰 재발급
  @PostMapping("/regeneration")
  public ResponseEntity<StatusResponse> regenerationAccess(HttpServletRequest request,
      HttpServletResponse response) {
    refreshService.regenerateAccessToken(request, response);
    return ResponseEntity.ok().body(StatusResponse.valueOf(ResponseMessages.SUCCESS));
  }
}
