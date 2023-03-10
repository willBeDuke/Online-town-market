package com.example.townmarket.common.domain.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

//lombok
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

//jpa
@Entity
public class ChatMessage {

  /**
   * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
   */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_message_id")
  private Long id;

  @Column
  private long productId;

  @Column
  private String sender;

  @Column
  private String receiver;

  @Column(columnDefinition = "TEXT")
  private String message;

  @CreatedDate
  @Column
  private LocalDateTime sendDate;


  /**
   * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
   */
  public ChatMessage(String sender, String receiver, String message, ChatRoom room) {
    this.sender = sender;
    this.receiver = receiver;
    this.message = message;
    this.productId = room.getProduct().getId();
    this.sendDate = LocalDateTime.now();
    this.room = room;
  }


  /**
   * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
   */
  @ManyToOne
  @JoinColumn(name = "chat_room_id")
  private ChatRoom room;

  /**
   * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
   */

  /**
   * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
   */
}
