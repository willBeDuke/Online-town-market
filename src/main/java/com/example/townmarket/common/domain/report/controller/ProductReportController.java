package com.example.townmarket.common.domain.report.controller;

import static com.example.townmarket.common.domain.report.controller.ProductReportController.PRODUCT_REPORT_URI_API;
import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_OK;

import com.example.townmarket.common.domain.report.dto.ProductReportRequestDto;
import com.example.townmarket.common.domain.report.sevice.ProductReportServiceImpl;
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
@RequestMapping(PRODUCT_REPORT_URI_API)
public class ProductReportController {

  static final String PRODUCT_REPORT_URI_API = "/report/product";
  private final ProductReportServiceImpl productReportService;

  @PostMapping
  public ResponseEntity<StatusResponse> reportProduct(
      @RequestBody ProductReportRequestDto productReportRequestDto, @AuthenticationPrincipal
  UserDetailsImpl userDetails) {
    productReportService.reportProduct(productReportRequestDto, userDetails.getUserId());
    return RESPONSE_OK;
  }
}
