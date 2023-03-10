package com.example.townmarket.common.domain.review.service;

import com.example.townmarket.common.domain.product.service.ProductServiceImpl;
import com.example.townmarket.common.domain.review.dto.CreateReviewRequestDto;
import com.example.townmarket.common.domain.review.dto.ReviewResponseDto;
import com.example.townmarket.common.domain.review.dto.UpdateReviewRequestDto;
import com.example.townmarket.common.domain.review.entity.Review;
import com.example.townmarket.common.domain.review.entity.UserGrade;
import com.example.townmarket.common.domain.review.repository.ReviewRepository;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.domain.user.service.UserServiceImpl;
import com.example.townmarket.common.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Lombok
@RequiredArgsConstructor
//jpa
@Service
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final UserServiceImpl userService;
  private final ProductServiceImpl productService;
  private final UserGradeServiceImpl userGradeService;

  @Override
  @Transactional
  public void createReview(CreateReviewRequestDto createReviewRequestDto, User reviewer) {
    if (reviewRepository.existsReviewByProductId(createReviewRequestDto.getProductId())) {
      throw new IllegalArgumentException("작성된 리뷰가 있습니다");
    }
    User reviewee = userService.findUserById(createReviewRequestDto.getRevieweeId());

    Review review = Review.builder()
        .reviewContents(createReviewRequestDto.getReviewContents())
        .reviewer(reviewer)
        .product(productService.findProductById(createReviewRequestDto.getProductId()))
        .build();
    reviewRepository.save(review);

    UserGrade userGrade = UserGrade.builder()
        .grade(createReviewRequestDto.getGrade())
        .review(review)
        .reviewee(reviewee).build();
    userGradeService.saveGrade(userGrade);
  }

  @Override
  @Transactional(readOnly = true)
  public ReviewResponseDto showSelectReview(Long reviewId) {
    if (!reviewRepository.existsReviewId(reviewId)) {
      throw new IllegalArgumentException("유효하지 않은 리뷰입니다");
    }
    return reviewRepository.searchByReviewId(reviewId);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ReviewResponseDto> showMyReviews(PageDto pageDto, User user) {
    return reviewRepository.searchByUserAndPaging(user, pageDto.toPageable());
  }

  @Override
  @Transactional
  public void updateMyReview(Long reviewId, Long userId,
      UpdateReviewRequestDto updateReviewRequestDto) {
    Review review = findReviewById(reviewId);
    reviewWriterCheck(review, userId);
    review.updateReview(updateReviewRequestDto);
    review.getUserGrade().setGrade(updateReviewRequestDto.getGrade());
  }

  @Override
  @Transactional
  public void deleteReview(Long reviewId, Long userId) {
    Review review = findReviewById(reviewId);
    reviewWriterCheck(review, userId);
    reviewRepository.deleteById(reviewId);
  }

  @Override
  public Review findReviewById(Long id) {
    return reviewRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("잘못된 Id입니다")
    );
  }

  public void reviewWriterCheck(Review review, Long userId) {
    if (!review.isReviewWriter(userId)) {
      throw new IllegalArgumentException("작성자가 아닙니다");
    }
  }


}
