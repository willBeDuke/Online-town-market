package com.example.townmarket.common.domain.user.dto;

import com.example.townmarket.common.domain.address.entity.Address;
import java.util.List;
import lombok.Getter;

@Getter
public class UserInfoAddressResponseDto {
  private String address;


  public UserInfoAddressResponseDto(Address address) {
    this.address = address.getAddress() +" "+ address.getAddress2() +" "+ address.getAddress3();

  }
}
