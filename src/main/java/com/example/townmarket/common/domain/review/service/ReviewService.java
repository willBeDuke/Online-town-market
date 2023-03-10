package com.example.townmarket.common.domain.review.service;

import com.example.townmarket.common.domain.review.dto.CreateReviewRequestDto;
import com.example.townmarket.common.domain.review.dto.ReviewResponseDto;
import com.example.townmarket.common.domain.review.dto.UpdateReviewRequestDto;
import com.example.townmarket.common.domain.review.entity.Review;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.dto.PageDto;
import org.springframework.data.domain.Page;

public interface ReviewService {

  void createReview(CreateReviewRequestDto createReviewRequestDto, User reviewer);

  ReviewResponseDto showSelectReview(Long reviewId);

  Page<ReviewResponseDto> showMyReviews(PageDto pageDto, User user);


  void updateMyReview(Long reviewId, Long userId, UpdateReviewRequestDto updateReviewRequestDto);

  void deleteReview(Long reviewId, Long userId);

  Review findReviewById(Long id);

}
