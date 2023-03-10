package com.example.townmarket.common.domain.report.entity;


import com.example.townmarket.common.TimeStamped;
import com.example.townmarket.common.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReport extends TimeStamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "report_id")
  private Long id;

  @Enumerated(EnumType.STRING)
  private ReportEnum reportEnum;

  @Column(nullable = false)
  private String reason;

  @Column(nullable = false)
  private Long reporterId;

  public enum ReportEnum{
    비매너, 욕설, 기타
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reported_id")
  private User reportedUser;
}
