package com.example.townmarket.common.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileRequestDto {

  private String nickname;
  private String img_url;

  public ProfileRequestDto(String nickname, String img_url) {
    this.nickname = nickname;
    this.img_url = img_url;
  }
}
