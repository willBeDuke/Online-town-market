package com.example.townmarket.common.domain.review.service;

import static com.example.townmarket.fixture.ReviewFixture.CREATE_REVIEW_REQUEST_DTO;
import static com.example.townmarket.fixture.ReviewFixture.REVIEW_ID;
import static com.example.townmarket.fixture.ReviewFixture.REVIEW_RESPONSE_DTO_PAGE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.townmarket.common.domain.product.service.ProductServiceImpl;
import com.example.townmarket.common.domain.review.dto.ReviewResponseDto;
import com.example.townmarket.common.domain.review.dto.UpdateReviewRequestDto;
import com.example.townmarket.common.domain.review.entity.Review;
import com.example.townmarket.common.domain.review.entity.UserGrade;
import com.example.townmarket.common.domain.review.repository.ReviewRepository;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.domain.user.service.UserServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;


@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

  @Mock
  UserServiceImpl userService;

  @Mock
  ProductServiceImpl productService;

  @Mock
  UserGradeServiceImpl userGradeService;

  @Mock
  ReviewRepository reviewRepository;

  @InjectMocks
  ReviewServiceImpl reviewService;


  @Test
  @DisplayName("리뷰 생성 성공 테스트")
  void createReview() {
    reviewService.createReview(CREATE_REVIEW_REQUEST_DTO, any());

    verify(reviewRepository, times(1)).save(any());
    verify(userGradeService, times(1)).saveGrade(any());
  }

  @Test
  @DisplayName("리뷰 선택 조회 성공 테스트")
  void showSelectReview() {
    given(reviewRepository.existsReviewId(REVIEW_ID)).willReturn(true);
    reviewService.showSelectReview(REVIEW_ID);

    verify(reviewRepository, times(1)).searchByReviewId(REVIEW_ID);

  }

  @Test
  @DisplayName("내 리뷰 목록 조회 성공 테스트")
  void showMyReviews() {
    given(reviewRepository.searchByUserAndPaging(any(), any())).willReturn(
        REVIEW_RESPONSE_DTO_PAGE);
    Page<ReviewResponseDto> reviewResponseDtos = reviewRepository.searchByUserAndPaging(any(),
        any());
    Assertions.assertSame(reviewResponseDtos, REVIEW_RESPONSE_DTO_PAGE);
  }

  @Test
  @DisplayName("내 리뷰 업데이트 성공 테스트")
  void updateMyReview() {
    //given
    Long reviewId = 1L;
    Long userId = 2L;
    Review review = Review.builder()
        .reviewContents("origin")
        .reviewer(new User(userId, null, null))
        .userGrade(new UserGrade(0, null, null))
        .build();

    UpdateReviewRequestDto updateReviewRequestDto = UpdateReviewRequestDto.builder()
        .reviewContents("update")
        .grade(5)
        .build();

    when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

    // when
    reviewService.updateMyReview(reviewId, userId, updateReviewRequestDto);

    // then
    assertThat(review.getReviewContents()).isEqualTo(updateReviewRequestDto.getReviewContents());
  }

  @Test
  @DisplayName("리뷰 삭제 성공 테스트")
  void deleteReview() {
    // given
    User user = mock(User.class);
    Review review = mock(Review.class);

    when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
    when(Optional.of(review).get().isReviewWriter(user.getId())).thenReturn(true);

    //when
    reviewService.deleteReview(review.getId(), user.getId());

    //then
    verify(reviewRepository, times(1)).deleteById(review.getId());
  }

  @Test
  @DisplayName("리뷰 리포지토리 조회 후 리뷰 반환 성공 테스트")
  void findReviewById() {
    //given
    Review review = mock(Review.class);

    when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));

    //when
    Review reviewResultFindById =  reviewService.findReviewById(review.getId());

    //then
    assertThat(reviewResultFindById).isEqualTo(review);
  }

  @Test
  @DisplayName("리뷰 작성자 체크 테스트")
  void reviewWriterCheck() {
      // Given
      Review review = mock(Review.class);
      when(review.isReviewWriter(1L)).thenReturn(true);
      when(review.isReviewWriter(2L)).thenThrow(IllegalArgumentException.class);

      // When & Then
      assertDoesNotThrow(() -> review.isReviewWriter(1L));
      assertThrows(IllegalArgumentException.class, () -> review.isReviewWriter(2L));

  }
}
