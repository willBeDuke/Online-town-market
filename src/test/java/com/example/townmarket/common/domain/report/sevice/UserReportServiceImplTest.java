package com.example.townmarket.common.domain.report.sevice;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.townmarket.common.domain.report.dto.PagingUserReportResponse;
import com.example.townmarket.common.domain.report.dto.UserReportRequest;
import com.example.townmarket.common.domain.report.entity.UserReport;
import com.example.townmarket.common.domain.report.entity.UserReport.ReportEnum;
import com.example.townmarket.common.domain.report.repository.UserReportRepository;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.domain.user.service.UserServiceImpl;
import com.example.townmarket.common.dto.PageDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class UserReportServiceImplTest {

  @Mock
  UserReportRepository userReportRepository;

  @Mock
  UserServiceImpl userService;

  @InjectMocks
  UserReportServiceImpl userReportService;


  @Test
  @DisplayName("중복 유저 신고 테스트")
  void reportUserDuplicated() {
    User user = User.builder().build();
    when(userService.findByUsername(any())).thenReturn(user);
    when(userReportRepository.existsByReporterIdAndReportedUser(any(), any())).thenReturn(true);

    UserReportRequest request = UserReportRequest.builder().build();
    Assertions.assertThatThrownBy(() -> {
      userReportService.reportUser(request, 1L);
    }).isInstanceOf(IllegalArgumentException.class);
  }


  @Test
  @DisplayName("유저 신고 테스트")
  void reportUser() {
    // given
    User user = User.builder().build();
    when(userService.findByUsername(any())).thenReturn(user);
    when(userReportRepository.existsByReporterIdAndReportedUser(any(), any())).thenReturn(false);

    UserReportRequest request = UserReportRequest.builder()
        .reportedUserName("user1")
        .reportEnum(ReportEnum.비매너)
        .reason("비매너 행동")
        .build();

    // when
    userReportService.reportUser(request, 1L);

    // 메서드에 넘겨진 인자를 빼오는 객체
    ArgumentCaptor<UserReport> argumentCaptor = ArgumentCaptor.forClass(UserReport.class);
    // 만약 save 메서드가 불리면 그때 넘겨진 인자를 빼오기
    verify(userReportRepository).save(argumentCaptor.capture());
    // 빼온 인자를 가져오기
    UserReport report = argumentCaptor.getValue();

    // then
    assertThat(report.getReportEnum()).isEqualTo(request.getReportEnum());
    assertThat(report.getReason()).isEqualTo(request.getReason());
  }


  @Test
  @DisplayName("신고받은 모든 유저 보기 테스트")
  void viewAllReportedUser() {
    // given
    PageDto pageDto = mock(PageDto.class);
    Pageable pageable = mock(Pageable.class);

    when(pageDto.toPageable()).thenReturn(pageable);
    when(userReportRepository.findAllReportedUserMax(pageable)).thenReturn(Page.empty());

    // when
    Page<PagingUserReportResponse> pagingUserReportResponse = userReportService.viewAllReportedUser(pageDto);

    // then
    assertThat(pagingUserReportResponse).isNotNull();

  }
}