package com.example.townmarket.common.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateReviewRequestDto {

  private String reviewContents;
  private int grade;

}
