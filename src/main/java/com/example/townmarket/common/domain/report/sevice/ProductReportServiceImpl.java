package com.example.townmarket.common.domain.report.sevice;

import com.example.townmarket.common.domain.product.service.ProductService;
import com.example.townmarket.common.domain.report.dto.ProductReportRequestDto;
import com.example.townmarket.common.domain.report.entity.ProductReport;
import com.example.townmarket.common.domain.report.repository.ProductReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductReportServiceImpl implements ProductReportService {

  private final ProductReportRepository productReportRepository;
  private final ProductService productService;

  @Override
  @Transactional
  public void reportProduct(ProductReportRequestDto productReportRequestDto, Long userId) {
    if (productReportRepository.existsByReportUserIdAndProductId(userId,
        productReportRequestDto.getProductId())) {
      throw new IllegalArgumentException("중복된 요청입니다.");
    }
    ProductReport productReport = ProductReport.builder()
        .reportUserId(userId)
        .reason(productReportRequestDto.getReason())
        .productId(productReportRequestDto.getProductId())
        .reportEnum(productReportRequestDto.getReportEnum())
        .build();
    productReportRepository.save(productReport);
    if (productReportRepository.countByProductId(productReportRequestDto.getProductId()) > 15) {
      productService.setBlock(productReportRequestDto.getProductId());
    }
  }
}
