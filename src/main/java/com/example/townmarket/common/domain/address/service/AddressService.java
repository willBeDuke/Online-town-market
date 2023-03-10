package com.example.townmarket.common.domain.address.service;

import com.example.townmarket.common.domain.address.dto.AddressResponseDto;
import com.example.townmarket.common.domain.address.entity.Address;
import com.example.townmarket.common.domain.user.entity.User;

public interface AddressService {

  AddressResponseDto getAddress(double x, double y, User user);

  AddressResponseDto getAddressSingUp(double x, double y);

  void addressSave(Address address);
}
