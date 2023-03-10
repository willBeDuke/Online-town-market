package com.example.townmarket.common.domain.user.dto;

import com.example.townmarket.common.domain.user.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {

  private String nickname;
  private String img_url;

  public ProfileResponseDto(Profile profile) {
    this.nickname = profile.getNickName();
    this.img_url = profile.getImg_url();
  }
}
