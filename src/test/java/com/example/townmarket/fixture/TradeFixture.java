package com.example.townmarket.fixture;

import static com.example.townmarket.fixture.UtilFixture.PAGE_DTO;

import com.example.townmarket.common.domain.trade.dto.CreateTradeDto;
import com.example.townmarket.common.domain.trade.dto.PagingTrade;
import java.util.Collections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class TradeFixture {

  public static final CreateTradeDto CREATE_TRADE_DTO =
      CreateTradeDto.builder()
          .productId(1L)
          .buyerId(1L)
          .build();

  public static final PagingTrade PAGING_TRADE =
      PagingTrade.builder()
          .productName("productName")
          .userName("userName")
          .build();

  public static final Page<PagingTrade> PAGING_TRADE_PAGE =
      new PageImpl<>(Collections.singletonList(PAGING_TRADE), PAGE_DTO.toPageable(), 0);
}
