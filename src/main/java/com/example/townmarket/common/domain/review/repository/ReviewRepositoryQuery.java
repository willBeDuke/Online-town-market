package com.example.townmarket.common.domain.review.repository;

import com.example.townmarket.common.domain.review.dto.ReviewResponseDto;
import com.example.townmarket.common.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryQuery {

  boolean existsReviewId(Long reviewId);

  ReviewResponseDto searchByReviewId(Long reviewId);

  Page<ReviewResponseDto> searchByUserAndPaging(User reviewer, Pageable pageable);
}
