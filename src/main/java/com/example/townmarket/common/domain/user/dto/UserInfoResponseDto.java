package com.example.townmarket.common.domain.user.dto;

import com.example.townmarket.common.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

  private String username;
  private String nickname;
  private String email;
  private String region;

  public UserInfoResponseDto(User user) {
    this.username = user.getUsername();
    this.nickname = user.getProfile().getNickName();
    this.email = user.getEmail();
    if (user.getRegion() != null) {
      this.region = user.getRegion();
    } else {
      this.region = "";
    }
  }
}