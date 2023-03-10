package com.example.townmarket.common.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponseDto {

  private Long reviewId;
  private String reviewContents;
  private String productName;
  private int grade;

  public ReviewResponseDto(Long reviewId, String reviewContents, String productName,
      int grade) {
    this.reviewId = reviewId;
    this.reviewContents = reviewContents;
    this.productName = productName;
    this.grade = grade;
  }
}
