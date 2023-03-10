package com.example.townmarket.common.domain.address.controller;

import com.example.townmarket.common.domain.address.dto.AddressResponseDto;
import com.example.townmarket.common.domain.address.service.AddressServiceImpl;
import com.example.townmarket.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class AddressController {

  private final AddressServiceImpl addressService;

  // 위경도를 통해 주소 반환
  @RequestMapping(value = "/address", method = {RequestMethod.GET, RequestMethod.PUT})
  public ResponseEntity<AddressResponseDto> getFullAddress(@RequestParam("x") double x,
      @RequestParam("y") double y, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    AddressResponseDto response = addressService.getAddress(x, y, userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/address/signup")
  public ResponseEntity<AddressResponseDto> getAddressSignUp(@RequestParam("x") double x,
      @RequestParam("y") double y) {
    AddressResponseDto response = addressService.getAddressSingUp(x, y);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
