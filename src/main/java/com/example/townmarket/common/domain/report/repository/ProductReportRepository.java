package com.example.townmarket.common.domain.report.repository;

import com.example.townmarket.common.domain.report.entity.ProductReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReportRepository extends JpaRepository<ProductReport, Long> {

  boolean existsByReportUserIdAndProductId(Long userId, Long productId);

  int countByProductId(Long productId);
}
