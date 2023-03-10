package com.example.townmarket.common.domain.user.entity;

import com.example.townmarket.common.TimeStamped;
import com.example.townmarket.common.domain.address.entity.Address;
import com.example.townmarket.common.domain.board.entity.Board;
import com.example.townmarket.common.domain.chat.entity.ChatRoom;
import com.example.townmarket.common.domain.interest.entity.Interest;
import com.example.townmarket.common.domain.product.entity.Product;
import com.example.townmarket.common.domain.report.entity.UserReport;
import com.example.townmarket.common.domain.review.entity.Review;
import com.example.townmarket.common.domain.review.entity.UserGrade;
import com.example.townmarket.common.domain.trade.entity.Trade;
import com.example.townmarket.common.enums.RoleEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

//lombok
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

//jpa
@Entity
@Table(name = "users")
@DynamicInsert
@DynamicUpdate
public class User extends TimeStamped {

  /**
   * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false)
  private Long id;

  @Column(length = 25, nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

//  @Column(nullable = false)
//  private String phoneNumber;

  @Column(nullable = false, unique = true)
  private String email;


  @Column
  @Enumerated(EnumType.STRING)
  private RoleEnum role;

  @Embedded
  private Profile profile;

  /**
   * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
   */

  @Builder
  public User(String username, String password, String email,
      Profile profile) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.profile = profile;
  }

  @Builder
  public User(String username, String password, Profile profile) {
    this.username = username;
    this.password = password;
    this.profile = profile;
  }

  //userdetails용 생성자
//  @Builder
//  public User(Long userId, String username, String password, RoleEnum roleEnum) {
//    this.id = userId;
//    this.username = username;
//    this.password = password;
//    this.role = roleEnum;
//  }

  public User(Long userId, String username, Profile profile) {
    this.id = userId;
    this.username = username;
    this.profile = profile;
  }


  /**
   * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
   */
  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Product> products = new LinkedHashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ChatRoom> chatRooms = new LinkedHashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "reviewer")
  private Set<Review> sendReviews = new LinkedHashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "user")
  private Set<Board> boards = new LinkedHashSet<>();
  @Builder.Default
  @OneToMany(mappedBy = "user")
  private Set<Interest> interests = new LinkedHashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "buyer")
  private Set<Trade> buyer = new LinkedHashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "seller")
  private Set<Trade> seller = new LinkedHashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "reviewee")
  private Set<UserGrade> grades = new LinkedHashSet<>();
  @Builder.Default
  @OneToMany(mappedBy = "reportedUser")
  private Set<UserReport> reports = new LinkedHashSet<>();


  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Address> address = new ArrayList<>();
  /**
   * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
   */

  /**
   * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
   */

  public boolean checkAuthorization(User user) {
    return Objects.equals(this.id, user.getId());
  }

  public void updatePassword(String password) {
    this.password = password;
  }


  public double getUserAverageGrade() {
    Set<UserGrade> grades = this.grades;
    long total = grades.stream().mapToInt(UserGrade::getGrade).sum();
    if (total == 0) {
      return 0;
    }
    return Math.abs(total / grades.size());
  }


}
