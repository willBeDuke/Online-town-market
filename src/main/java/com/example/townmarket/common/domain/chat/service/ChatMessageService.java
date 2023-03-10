package com.example.townmarket.common.domain.chat.service;

import com.example.townmarket.common.domain.chat.dto.ChatMessageDto;

public interface ChatMessageService {

  ChatMessageDto createChat(ChatMessageDto message, Long roomId);
}
