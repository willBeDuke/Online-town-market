package com.example.townmarket.fixture;

import static com.example.townmarket.fixture.ProductFixture.PRODUCT;
import static com.example.townmarket.fixture.UserFixture.USER1;
import static com.example.townmarket.fixture.UtilFixture.PAGE_DTO;

import com.example.townmarket.common.domain.review.dto.CreateReviewRequestDto;
import com.example.townmarket.common.domain.review.dto.ReviewResponseDto;
import com.example.townmarket.common.domain.review.dto.UpdateReviewRequestDto;
import com.example.townmarket.common.domain.review.entity.Review;
import java.util.Collections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class ReviewFixture {

  public static final CreateReviewRequestDto CREATE_REVIEW_REQUEST_DTO = CreateReviewRequestDto.builder()
      .revieweeId(1L)
      .grade(1)
      .productId(1L)
      .reviewContents("reviewContents")
      .build();

  public static final ReviewResponseDto REVIEW_RESPONSE_DTO = ReviewResponseDto.builder()
      .reviewId(1L)
      .productName("productName1")
      .reviewContents("review")
      .grade(1)
      .build();

  public static final Review REVIEW = Review.builder()
      .reviewContents("reviewContents")
      .reviewer(USER1)
      .product(PRODUCT)
      .build();

  public static final Page<ReviewResponseDto> REVIEW_RESPONSE_DTO_PAGE = new PageImpl<>(
      Collections.singletonList(REVIEW_RESPONSE_DTO), PAGE_DTO.toPageable(), 1);

  public static final UpdateReviewRequestDto UPDATE_REVIEW_REQUEST_DTO = UpdateReviewRequestDto.builder()
      .reviewContents("reviewContents")
      .grade(1)
      .build();


  public static final Long REVIEW_ID = 1L;

}
