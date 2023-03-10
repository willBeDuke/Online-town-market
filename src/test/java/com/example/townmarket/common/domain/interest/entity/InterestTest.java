package com.example.townmarket.common.domain.interest.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.townmarket.common.domain.product.entity.Product;
import com.example.townmarket.common.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InterestTest {

  @Mock
  User user;

  @Mock
  Product product;

  @Test
  @DisplayName("관심내역 작성자 확인")
  void isInterestWriter() {

    //given
    Long userId = 1L;

    Interest interest = Interest.builder()
        .user(user)
        .product(product)
        .build();

    when(user.getId()).thenReturn(userId);

    //when
    boolean result = interest.isInterestWriter(1L);

    //then
    assertTrue(result);
  }
}