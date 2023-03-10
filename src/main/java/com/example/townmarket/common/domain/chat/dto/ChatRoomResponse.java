package com.example.townmarket.common.domain.chat.dto;

import com.example.townmarket.common.domain.user.entity.User;
import lombok.Getter;

@Getter
public class ChatRoomResponse {

  private Long productId;
  private Long buyerId;
  private String profileImg;
  private String nickname;
  private String region;
  private String productName;
  private Long roomId;

  public ChatRoomResponse(Long productId, User user,
      String productName, Long roomId) {
    this.productId = productId;
    this.buyerId = user.getId();
    this.profileImg = user.getProfile().getImg_url();
    this.nickname = user.getProfile().getNickName();
    this.region = user.getAddress().get(0).getAddress3();
    this.productName = productName;
    this.roomId = roomId;
  }
}
