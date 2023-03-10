package com.example.townmarket.common.domain.user.dto;

import com.example.townmarket.common.domain.address.entity.Address;
import com.example.townmarket.common.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

  private String username;
  private String nickname;
  private String email;
  private List<UserInfoAddressResponseDto> address;

  public UserInfoResponseDto(User user) {
    this.username = user.getUsername();
    this.nickname = user.getProfile().getNickName();
    this.email = user.getEmail();
    this.address = user.getAddress().stream().map(UserInfoAddressResponseDto::new).collect(Collectors.toList());
  }
}
