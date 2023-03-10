package com.example.townmarket.common.domain.report.sevice;

import com.example.townmarket.common.domain.report.dto.ProductReportRequestDto;

public interface ProductReportService {

  void reportProduct(ProductReportRequestDto productReportRequestDto, Long userId);
}
