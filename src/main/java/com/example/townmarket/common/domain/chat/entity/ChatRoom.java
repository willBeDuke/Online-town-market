package com.example.townmarket.common.domain.chat.entity;

import com.example.townmarket.common.domain.product.entity.Product;
import com.example.townmarket.common.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

  /**
   * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_room_id")
  private Long id;

  @Column(name = "room_name")
  private String productName;

  @Column(nullable = false, name = "seller_id")
  private Long seller;


  /**
   * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
   */
  public ChatRoom(Product product, User buyer) {
    this.productName = product.getProductName();
    this.seller = product.getUser().getId();
    this.buyer = buyer;
    this.product = product;
  }


  /**
   * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
   */
  @ManyToOne
  @JoinColumn(name = "buyer_id")
  private User buyer;
  //
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Builder.Default
  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ChatMessage> message = new LinkedHashSet<>();

  /**
   * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
   */

  /**
   * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
   */
}
