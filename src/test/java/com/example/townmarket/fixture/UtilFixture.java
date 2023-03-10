package com.example.townmarket.fixture;

import com.example.townmarket.common.dto.PageDto;

public class UtilFixture {
  public static final PageDto PAGE_DTO =
      PageDto.builder().page(1).size(10).isAsc(false).keyword("ooo").sortBy("createdAt").build();


}
