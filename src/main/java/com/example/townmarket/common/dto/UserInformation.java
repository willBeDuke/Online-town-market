package com.example.townmarket.common.dto;

import com.example.townmarket.common.enums.RoleEnum;
import lombok.Getter;

@Getter
public class UserInformation {

  private Long userId;
  private String username;
  private String password;
  private RoleEnum roleEnum;

  public UserInformation(Long userId, String username, String password, RoleEnum roleEnum) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.roleEnum = roleEnum;
  }
}
