package com.example.townmarket.common.domain.report.sevice;

import com.example.townmarket.common.domain.report.dto.PagingUserReportResponse;
import com.example.townmarket.common.domain.report.dto.UserReportRequest;
import com.example.townmarket.common.dto.PageDto;
import org.springframework.data.domain.Page;

public interface UserReportService {

  void reportUser(UserReportRequest userReportRequest, Long userId);

  Page<PagingUserReportResponse> viewAllReportedUser(PageDto pageDto);
}
