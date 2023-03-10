package com.example.townmarket.common.domain.product.entity;

import com.example.townmarket.common.TimeStamped;
import com.example.townmarket.common.domain.chat.entity.ChatRoom;
import com.example.townmarket.common.domain.interest.entity.Interest;
import com.example.townmarket.common.domain.product.dto.ProductRequestDto;
import com.example.townmarket.common.domain.user.entity.User;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

//lombok
@Getter
@NoArgsConstructor
@AllArgsConstructor
//jpa
@Entity
@DynamicInsert
@DynamicUpdate
public class Product extends TimeStamped {

  /**
   * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_Id", nullable = false)
  private Long id;
  private String productName;
  private Long productPrice;
  private String productContents;

  private String productImg;
  @Enumerated(EnumType.STRING)
  private ProductEnum productEnum;
  @Enumerated(EnumType.STRING)
  private ProductStatus productStatus;
  @Enumerated(EnumType.STRING)
  private ProductCategory productCategory;
  @Column
  private boolean block;

  public void updateProductEnum() {
    this.productEnum = ProductEnum.판매완료;
  }

  private Long viewCount = 0L;

  public enum ProductEnum {
    나눔, 나눔_완료, 판매_중, 예약, 판매완료
  }

  public enum ProductStatus {
    S, A, B, C, D
  }

  public enum ProductCategory {
    IT, WEAR, FOOD, GAME, CAR, TICKET
  }

  /**
   * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
   */
  @Builder
  public Product(Long id, String productName, Long productPrice, ProductStatus productStatus,
      String productContents,
      ProductCategory productCategory, ProductEnum productEnum, String productImg, User user) {
    this.id = id;
    this.productName = productName;
    this.productPrice = productPrice;
    this.productStatus = productStatus;
    this.productContents = productContents;
    this.productEnum = productEnum;
    this.productCategory = productCategory;
    this.productImg = productImg;
    this.user = user;
  }

  @Builder
  public Product(User user) {
    this.user = user;
  }

  /**
   * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ChatRoom> room = new LinkedHashSet<>();


  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Interest> interest = new LinkedHashSet<>();
  /**
   * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
   */

  /**
   * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
   */

  public void update(ProductRequestDto productRequestDto) {
    this.productName = productRequestDto.getProductName();
    this.productPrice = productRequestDto.getProductPrice();
    this.productStatus = productRequestDto.getProductStatus();
    this.productEnum = productRequestDto.getProductEnum();
    this.productCategory = productRequestDto.getProductCategory();
  }

  public boolean checkProductWriter(Long userId) {
    return this.user.getId().equals(userId);
  }

  public Set<ChatRoom> getChatRooms() {
    return this.room;
  }

  public boolean checkProductEnum(ProductEnum productEnum) {
    return productEnum.equals(ProductEnum.판매완료);
  }

  public boolean isBlocked() {
    return this.block;
  }

  public void setBlock() {
    this.block = true;
  }

  public void updateView() {
    this.viewCount += 1L;
  }

}
