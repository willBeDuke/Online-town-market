package com.example.townmarket.common.domain.report.entity;

import com.example.townmarket.common.TimeStamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Lombok
@Getter
@NoArgsConstructor
//jpa
@Entity
public class ProductReport extends TimeStamped {

  /**
   * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_report_id", nullable = false)
  private Long id;
  @Enumerated(EnumType.STRING)
  private ReportEnum reportEnum;
  @Column
  private String reason;

  @Column
  private Long reportUserId;

  public enum ReportEnum {
    광고, 유해매체, 기타
  }

  @Column
  private Long productId;

  /**
   * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
   */
  @Builder
  public ProductReport(String reason, Long reportUserId, ReportEnum reportEnum, Long productId) {
    this.reason = reason;
    this.reportUserId = reportUserId;
    this.productId = productId;
    this.reportEnum = reportEnum;
  }
/**
 * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
 */

/**
 * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
 */

/**
 * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
 */
}
