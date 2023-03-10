package com.example.townmarket.common.domain.chat.repository;

import static com.example.townmarket.common.domain.chat.entity.QChatMessage.chatMessage;

import com.example.townmarket.common.domain.chat.dto.ChatMessageDto;
import com.example.townmarket.common.domain.chat.entity.ChatRoom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatMessageRepositoryQueryImpl implements ChatMessageRepositoryQuery {

  private final JPAQueryFactory jpaQueryFactory;


  @Override
  public List<ChatMessageDto> getChatRoomByChatRoom(ChatRoom chatRoom) {
    return jpaQueryFactory.select((Projections.constructor(
            ChatMessageDto.class,
            chatMessage.sender,
            chatMessage.receiver,
            chatMessage.message,
            chatMessage.sendDate)))
        .from(chatMessage)
        .where(chatMessage.room.eq(chatRoom))
        .orderBy(chatMessage.sendDate.asc())
        .setHint("org.hibernate.readOnly", true)
        .fetch();
  }
}
