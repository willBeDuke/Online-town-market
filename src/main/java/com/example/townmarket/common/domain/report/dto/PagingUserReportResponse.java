package com.example.townmarket.common.domain.report.dto;

import com.example.townmarket.common.domain.report.entity.UserReport.ReportEnum;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingUserReportResponse {

  private String reportedName;
  private String reason;
  private ReportEnum reportEnum;
}
