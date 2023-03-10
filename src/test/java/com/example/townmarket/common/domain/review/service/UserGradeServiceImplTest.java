package com.example.townmarket.common.domain.review.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.townmarket.common.domain.review.entity.Review;
import com.example.townmarket.common.domain.review.entity.UserGrade;
import com.example.townmarket.common.domain.review.repository.UserGradeRepository;
import com.example.townmarket.common.domain.user.entity.User;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserGradeServiceImplTest {
  @Mock
  private UserGradeRepository userGradeRepository;

  @InjectMocks
  private UserGradeServiceImpl userGradeService;

  @Test
  @DisplayName("별점 저장 성공 테스트")
  void saveGrade() {
    //given
    UserGrade userGrade = UserGrade.builder()
        .grade(5)
        .reviewee(new User(1L, null, null))
        .review(new Review("content", null, null, null))
        .build();

    //when
    userGradeService.saveGrade(userGrade);

    //then
    verify(userGradeRepository, times(1)).save(userGrade);

  }

  @Test
  @DisplayName("별점 업데이트 성공 테스트")
  void updateGrade() {
    // given
    UserGrade userGrade = UserGrade.builder()
        .grade(5)
        .reviewee(new User(1L, null, null))
        .review(new Review("content", null, null, null))
        .build();

    int newGrade = 10;

    // when
    userGradeService.updateGrade(userGrade, newGrade);

    // then
    assertEquals(newGrade, userGrade.getGrade());

  }

  @Test
  @DisplayName("평가자 모두 찾기 테스트")
  void findAllByReviewee() {
    // given
    User reviewee = User.builder()
        .id(1L)
        .build();

    UserGrade userGrade1 = UserGrade.builder()
        .grade(5)
        .reviewee(new User(1L, null, null))
        .review(new Review("content", null, null, null))
        .build();

    UserGrade userGrade2 = UserGrade.builder()
        .grade(5)
        .reviewee(new User(2L, null, null))
        .review(new Review("content2", null, null, null))
        .build();

    Set<UserGrade> userGrades = new HashSet<>();
    userGrades.add(userGrade1);
    userGrades.add(userGrade2);

    when(userGradeRepository.findAllByReviewee(reviewee)).thenReturn(userGrades);

    // When
    Set<UserGrade> result = userGradeService.findAllByReviewee(reviewee);

    // Then
    assertEquals(userGrades.size(), result.size());
    assertEquals(userGrades, result);
  }

}