package com.example.townmarket.fixture;

import static com.example.townmarket.fixture.UtilFixture.PAGE_DTO;

import com.example.townmarket.common.domain.report.dto.PagingUserReportResponse;
import com.example.townmarket.common.domain.report.dto.ProductReportRequestDto;
import com.example.townmarket.common.domain.report.dto.UserReportRequest;
import com.example.townmarket.common.domain.report.entity.ProductReport;
import com.example.townmarket.common.domain.report.entity.UserReport.ReportEnum;
import java.util.Collections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class ReportFixture {

  public static final ProductReportRequestDto PRODUCT_REPORT_REQUEST_DTO =
      ProductReportRequestDto.builder()
          .reportEnum(ProductReport.ReportEnum.광고)
          .reason("상세사유")
          .productId(1L)
          .build();

  public static final UserReportRequest USER_REPORT_REQUEST =
      UserReportRequest.builder()
          .reportedUserName("user1")
          .reason("신고 이유")
          .reportEnum(ReportEnum.욕설)
          .build();

  public static final PagingUserReportResponse PAGING_USER_REPORT_RESPONSE =
      PagingUserReportResponse.builder()
          .reportedName("user1")
          .reason("신고이유")
          .reportEnum(ReportEnum.욕설).build();

  public static final Page<PagingUserReportResponse> PAGING_USER_REPORT_RESPONSE_LIST =
      new PageImpl<>(Collections.singletonList(PAGING_USER_REPORT_RESPONSE), PAGE_DTO.toPageable(),
          0);


}
