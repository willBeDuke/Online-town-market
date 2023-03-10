package com.example.townmarket.common.domain.chat.repository;

import com.example.townmarket.common.domain.chat.dto.ChatMessageDto;
import com.example.townmarket.common.domain.chat.entity.ChatRoom;
import java.util.List;

public interface ChatMessageRepositoryQuery {

  List<ChatMessageDto> getChatRoomByChatRoom(ChatRoom chatRoom);
}
