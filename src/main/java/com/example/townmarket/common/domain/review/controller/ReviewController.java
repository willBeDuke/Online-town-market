package com.example.townmarket.common.domain.review.controller;

import static com.example.townmarket.common.domain.review.controller.ReviewController.REVIEW_API_URI;
import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_CREATED;
import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_DELETE;
import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_OK;

import com.example.townmarket.common.domain.review.dto.CreateReviewRequestDto;
import com.example.townmarket.common.domain.review.dto.ReviewResponseDto;
import com.example.townmarket.common.domain.review.dto.UpdateReviewRequestDto;
import com.example.townmarket.common.domain.review.service.ReviewServiceImpl;
import com.example.townmarket.common.dto.PageDto;
import com.example.townmarket.common.dto.StatusResponse;
import com.example.townmarket.common.security.UserDetailsImpl;
import com.example.townmarket.common.util.SetHttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(REVIEW_API_URI)
public class ReviewController {


  public static final String REVIEW_API_URI = "/review";
  private final ReviewServiceImpl reviewService;
  private final SetHttpHeaders setHttpHeaders;

  @PostMapping
  public ResponseEntity<StatusResponse> createReview(
      @RequestBody CreateReviewRequestDto createReviewRequestDto, @AuthenticationPrincipal
  UserDetailsImpl userDetails) {
    reviewService.createReview(createReviewRequestDto, userDetails.getUser());
    return RESPONSE_CREATED;
  }

  @GetMapping("/{reviewId}")
  public ResponseEntity<ReviewResponseDto> showSelectReview(@PathVariable Long reviewId
  ) {
    return ResponseEntity.ok().headers(setHttpHeaders.setHeaderTypeJson())
        .body(reviewService.showSelectReview(reviewId));
  }


  @GetMapping("/my")
  public ResponseEntity<Page<ReviewResponseDto>> showMyReviews(@ModelAttribute PageDto pageDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return ResponseEntity.ok().headers(setHttpHeaders.setHeaderTypeJson())
        .body(reviewService.showMyReviews(pageDto, userDetails.getUser()));
  }

  @PatchMapping("/update/{reviewId}")
  public ResponseEntity<StatusResponse> updateMyReview(@PathVariable Long reviewId,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody UpdateReviewRequestDto updateReviewRequestDto
  ) {

    reviewService.updateMyReview(reviewId, userDetails.getUserId(), updateReviewRequestDto);
    return RESPONSE_OK;
  }

  @DeleteMapping("/delete/{reviewId}")
  public ResponseEntity<StatusResponse> deleteMyReview(@PathVariable Long reviewId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    reviewService.deleteReview(reviewId, userDetails.getUserId());
//    StatusResponse statusResponse = StatusResponse.valueOf(ResponseMessages.DELETE_SUCCESS);
    return RESPONSE_DELETE;
  }

}
