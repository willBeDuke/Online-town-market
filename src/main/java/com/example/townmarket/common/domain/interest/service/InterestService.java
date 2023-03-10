package com.example.townmarket.common.domain.interest.service;

import com.example.townmarket.common.domain.interest.dto.InterestPagingResponseDto;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.dto.PageDto;
import org.springframework.data.domain.Page;

public interface InterestService {

  boolean addInterest(User user, Long product);

  Page<InterestPagingResponseDto> showMyInterestProducts(User user, PageDto pageDto);

  boolean checkInterest(User user, Long productId);
}
