package com.example.townmarket.common.redis.service;

import com.example.townmarket.common.redis.entity.Tokens;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RefreshService {

  void saveBlackList(Tokens tokens); // 로그아웃 시 블랙리스트에 해당 토큰들을 저장하기 위해 필요

  void regenerateAccessToken(HttpServletRequest request, HttpServletResponse response);
}
