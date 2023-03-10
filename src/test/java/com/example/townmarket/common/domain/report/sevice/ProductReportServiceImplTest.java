package com.example.townmarket.common.domain.report.sevice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.townmarket.common.domain.product.service.ProductService;
import com.example.townmarket.common.domain.report.dto.ProductReportRequestDto;
import com.example.townmarket.common.domain.report.entity.ProductReport;
import com.example.townmarket.common.domain.report.entity.ProductReport.ReportEnum;
import com.example.townmarket.common.domain.report.repository.ProductReportRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductReportServiceImplTest {

  @Mock
  private ProductReportRepository productReportRepository;

  @Mock
  private ProductService productService;

  @InjectMocks
  ProductReportServiceImpl productReportService;


  @Test
  @DisplayName("중복 상품 신고 테스트")
  void reportProductDuplicated() {
    when(productReportRepository.existsByReportUserIdAndProductId(any(), any())).thenReturn(true);

    Assertions.assertThatThrownBy(()-> productReportService.reportProduct(
            ProductReportRequestDto.builder().build(), 1L)).isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("중복");
  }


  @Test
  @DisplayName("상품 신고 테스트")
  void reportProduct() {
    // given
    when(productReportRepository.existsByReportUserIdAndProductId(any(), any())).thenReturn(false);

    ProductReportRequestDto requestDto = ProductReportRequestDto.builder()
        .reason("이유")
        .productId(1L)
        .reportEnum(ReportEnum.광고)
        .build();

    // when
    productReportService.reportProduct(requestDto, 1L);

    // then
    ArgumentCaptor<ProductReport> argumentCaptor = ArgumentCaptor.forClass(ProductReport.class);
    verify(productReportRepository).save(argumentCaptor.capture());
    ProductReport productReport = argumentCaptor.getValue();

    assertThat(productReport.getReportEnum()).isEqualTo(requestDto.getReportEnum());
    assertThat(productReport.getProductId()).isEqualTo(requestDto.getProductId());
    assertThat(productReport.getReason()).isEqualTo(requestDto.getReason());
  }


  @Test
  @DisplayName("상품 신고 횟수가 15회 이상일 떄")
  void reportProductOver15() {
    // given
    when(productReportRepository.existsByReportUserIdAndProductId(any(), any())).thenReturn(false);
    when(productReportRepository.countByProductId(any())).thenReturn(16);

    // when
    productReportService.reportProduct(ProductReportRequestDto.builder().build(), 1L);

    // then
    verify(productService, times(1)).setBlock(any());
  }

}