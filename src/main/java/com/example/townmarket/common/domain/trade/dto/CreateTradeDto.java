package com.example.townmarket.common.domain.trade.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTradeDto {
  private Long productId;
  private Long buyerId;

}
