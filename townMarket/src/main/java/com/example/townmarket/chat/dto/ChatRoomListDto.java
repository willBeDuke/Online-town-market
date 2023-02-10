package com.example.townmarket.chat.dto;

import com.example.townmarket.chat.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomListDto {

  private long roomId;
  private String roomName;


  public ChatRoomListDto(ChatRoom room) {
    this.roomId = room.getId();
    this.roomName = room.getRoomName();
  }
}
