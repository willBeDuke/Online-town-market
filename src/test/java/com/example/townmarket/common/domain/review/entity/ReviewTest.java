package com.example.townmarket.common.domain.review.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.townmarket.common.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewTest {

  @Mock
  User user;

  @Test
  @DisplayName("리뷰 작성자 확인")
  void isReviewWriter() {
    // given
    Long reviewerId = 1L;
    Review review = Review.builder().reviewer(user).build();
    when(user.getId()).thenReturn(reviewerId);

    // when
    boolean result = review.isReviewWriter(reviewerId);

    // then
    assertTrue(result);
  }
}