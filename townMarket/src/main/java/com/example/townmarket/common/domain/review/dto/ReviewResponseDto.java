package com.example.townmarket.common.domain.review.dto;

import com.example.townmarket.common.domain.user.entity.Profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReviewResponseDto {

  private int grade;
  private String reviewContents;
  private Profile reviewerProfile;
  private Profile revieweeProfile;
  private String productName;

}