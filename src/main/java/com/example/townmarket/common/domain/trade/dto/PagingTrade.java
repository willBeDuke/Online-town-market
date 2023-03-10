package com.example.townmarket.common.domain.trade.dto;

import com.example.townmarket.common.domain.trade.entity.Trade;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PagingTrade {

  private String userName;

  private String productName;

  public PagingTrade(String userName, String productName) {
    this.productName = productName;
    this.userName = userName;
  }
}
