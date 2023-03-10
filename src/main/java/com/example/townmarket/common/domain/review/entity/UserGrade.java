package com.example.townmarket.common.domain.review.entity;


import com.example.townmarket.common.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//lombok
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//jpa
@Entity
public class UserGrade {

  /**
   * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "grade_id", nullable = false)
  private Long id;

  @Column
  @Max(5)
  private int grade;


  /**
   * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
   */
  @Builder
  public UserGrade(int grade, User reviewee, Review review) {
    this.grade = grade;
    this.reviewee = reviewee;
    this.review = review;
  }

  /**
   * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User reviewee;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "review_id")
  private Review review;
  /**
   * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
   */

  /**
   * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
   */
  public void setGrade(int grade) {
    this.grade = grade;
  }
}
