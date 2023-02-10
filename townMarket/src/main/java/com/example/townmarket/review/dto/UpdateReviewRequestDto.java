package com.example.townmarket.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateReviewRequestDto {

  private Long reviewId;
  private String review;
  private int grade;

}