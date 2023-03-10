package com.example.townmarket.common.domain.chat.dto;

import com.example.townmarket.common.domain.chat.entity.ChatRoom;
import com.example.townmarket.common.domain.product.entity.Product;
import com.example.townmarket.common.domain.user.entity.User;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomListDtailDto {

  private long userId;
  private Set<ChatRoomListDto> roomList;


  public ChatRoomListDtailDto(User user) {
    this.userId = user.getId();
    Set<ChatRoomListDto> roomList = new LinkedHashSet<>();
    // 판매자인 경우
    if (!user.getProducts().isEmpty()) {
      for (Product product : user.getProducts()) {
        for (ChatRoom chatRoom : product.getChatRooms()) {
          roomList.add(new ChatRoomListDto(chatRoom, user));
        }
      }
    }
    // 구매자인 경우
    else {
      for (ChatRoom chatRoom : user.getChatRooms()) {
        roomList.add(new ChatRoomListDto(chatRoom, user));
        if (chatRoom.getProduct().getUser().getId() == user.getId()) {
          roomList.add(new ChatRoomListDto(chatRoom, chatRoom.getBuyer()));
        }
      }
    }
    this.roomList = roomList;
  }
}

