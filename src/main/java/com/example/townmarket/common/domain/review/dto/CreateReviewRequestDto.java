package com.example.townmarket.common.domain.review.dto;

import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateReviewRequestDto {

  private Long revieweeId;
  private String reviewContents;
  @Max(5)
  private int grade;
  private Long productId;

  @Builder
  public CreateReviewRequestDto(Long revieweeId, String reviewContents, int grade, Long productId) {
    this.revieweeId = revieweeId;
    this.reviewContents = reviewContents;
    this.grade = grade;
    this.productId = productId;
  }
}
