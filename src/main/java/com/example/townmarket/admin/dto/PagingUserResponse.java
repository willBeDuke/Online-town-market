package com.example.townmarket.admin.dto;

import com.example.townmarket.common.domain.user.entity.Profile;
import com.example.townmarket.common.domain.user.entity.User;
import lombok.Getter;

@Getter
public class PagingUserResponse {

  private final String username;

  private final Profile profile;

  public PagingUserResponse(User user) {
    this.username = user.getUsername();
    this.profile = user.getProfile();
  }
}
