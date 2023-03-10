package com.example.townmarket.common.domain.report.dto;

import com.example.townmarket.common.domain.report.entity.ProductReport.ReportEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductReportRequestDto {

  private ReportEnum reportEnum;
  private String reason;
  private Long productId;

}
