package com.example.townmarket.fixture;

import static com.example.townmarket.fixture.UtilFixture.PAGE_DTO;

import com.example.townmarket.common.domain.interest.dto.InterestPagingResponseDto;
import java.util.Collections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class InterestFixture {

  public static final InterestPagingResponseDto INTEREST_PAGING_RESPONSE_DTO
      = InterestPagingResponseDto.builder()
      .productId(1L)
      .productImg("img_url")
      .productName("productName")
      .productPrice(1000L)
      .build();

  public static final Page<InterestPagingResponseDto> INTEREST_PAGING_RESPONSE_DTO_PAGE =
      new PageImpl<>(Collections.singletonList(INTEREST_PAGING_RESPONSE_DTO), PAGE_DTO.toPageable(),
          0);

}
