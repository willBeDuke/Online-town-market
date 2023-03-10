package com.example.townmarket.common.domain.report.controller;

import static com.example.townmarket.common.domain.report.controller.ProductReportController.PRODUCT_REPORT_URI_API;
import static com.example.townmarket.common.domain.report.controller.UserReportController.USER_REPORT_URI_API;
import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_CREATED;
import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_OK;

import com.example.townmarket.common.domain.report.dto.ProductReportRequestDto;
import com.example.townmarket.common.domain.report.dto.UserReportRequest;
import com.example.townmarket.common.domain.report.entity.UserReport;
import com.example.townmarket.common.domain.report.sevice.ProductReportService;
import com.example.townmarket.common.domain.report.sevice.ProductReportServiceImpl;
import com.example.townmarket.common.domain.report.sevice.UserReportService;
import com.example.townmarket.common.dto.StatusResponse;
import com.example.townmarket.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(USER_REPORT_URI_API )
public class UserReportController {

  static final String USER_REPORT_URI_API = "/report/user";
  private final UserReportService userReportService;

  @PostMapping
  public ResponseEntity<StatusResponse> reportUser(
      @RequestBody UserReportRequest userReportRequest, @AuthenticationPrincipal
  UserDetailsImpl userDetails) {
    userReportService.reportUser(userReportRequest, userDetails.getUserId());
    return RESPONSE_CREATED;
  }
}
