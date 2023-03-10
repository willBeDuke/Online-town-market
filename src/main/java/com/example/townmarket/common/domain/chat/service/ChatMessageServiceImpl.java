package com.example.townmarket.common.domain.chat.service;

import com.example.townmarket.common.domain.chat.dto.ChatMessageDto;
import com.example.townmarket.common.domain.chat.entity.ChatMessage;
import com.example.townmarket.common.domain.chat.entity.ChatRoom;
import com.example.townmarket.common.domain.chat.repository.ChatMessageRepository;
import com.example.townmarket.common.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

  private final ChatMessageRepository messageRepository;
  private final ChatRoomRepository roomRepository;

  /**
   * 채팅 생성
   */
  @Override
  @Transactional
  public ChatMessageDto createChat(ChatMessageDto message, Long roomId) {

    ChatRoom room = roomRepository.findById(roomId).orElseThrow(
        () -> new IllegalArgumentException("채팅방이 존재하지 않습니다.")
    );
    ChatMessage addMessage = new ChatMessage(message.getSender(), message.getReceiver(),
        message.getMessage(), room);
    messageRepository.save(addMessage);


    return message;
    }

  }

