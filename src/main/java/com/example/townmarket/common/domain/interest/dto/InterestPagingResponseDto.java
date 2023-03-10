package com.example.townmarket.common.domain.interest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InterestPagingResponseDto {

  private String productImg;
  private String productName;
  private Long productPrice;
  private Long productId;

  public InterestPagingResponseDto(String productImg, String productName, Long productPrice, Long productId) {
    this.productImg = productImg;
    this.productName = productName;
    this.productPrice = productPrice;
    this.productId = productId;
  }
}
