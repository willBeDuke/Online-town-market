package com.example.townmarket.common.domain.report.sevice;

import com.example.townmarket.common.domain.report.dto.PagingUserReportResponse;
import com.example.townmarket.common.domain.report.dto.UserReportRequest;
import com.example.townmarket.common.domain.report.entity.UserReport;
import com.example.townmarket.common.domain.report.repository.UserReportRepository;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.domain.user.service.UserService;
import com.example.townmarket.common.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReportServiceImpl implements UserReportService {

  private final UserReportRepository userReportRepository;
  private final UserService userService;

  @Override
  public void reportUser(UserReportRequest userReportRequest, Long userId) {
    User reported = userService.findByUsername(userReportRequest.getReportedUserName());
    if (userReportRepository.existsByReporterIdAndReportedUser(userId, reported)) {
      throw new IllegalArgumentException("중복 신고는 금지되어있습니다");
    }
    UserReport userReport = UserReport.builder()
        .reportedUser(reported)
        .reportEnum(userReportRequest.getReportEnum())
        .reporterId(userId)
        .reason(userReportRequest.getReason())
        .build();
    userReportRepository.save(userReport);
  }

  @Override
  public Page<PagingUserReportResponse> viewAllReportedUser(PageDto pageDto) {
    return userReportRepository.findAllReportedUserMax(pageDto.toPageable());
  }
}
