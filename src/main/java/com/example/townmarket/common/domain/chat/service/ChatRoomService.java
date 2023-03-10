package com.example.townmarket.common.domain.chat.service;

import com.example.townmarket.common.domain.chat.dto.ChatMessageDto;
import com.example.townmarket.common.domain.chat.dto.ChatRoomResponse;
import com.example.townmarket.common.domain.user.entity.User;
import java.util.List;

public interface ChatRoomService {

  void createRoom(Long productId, String username);

  void deleteChat(Long roomId, String username);

  List<ChatMessageDto> getChatRoom(Long roomId, String username);

  List<ChatRoomResponse> buyChatList(Long userId);

  List<ChatRoomResponse> sellChatList(Long userId);

  boolean checkRoom(Long productId, User user);
}
