package com.example.townmarket.common.domain.chat.dto;


import java.time.LocalDateTime;

import com.example.townmarket.common.domain.chat.entity.ChatMessage;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ChatMessageDto {

  private String sender;
  private String receiver;
  private String message;
  private LocalDateTime sendDate;

  public ChatMessageDto(String sender, String receiver, String message, LocalDateTime sendDate) {
    this.sender = sender;
    this.receiver = receiver;
    this.message = message;
    this.sendDate = sendDate;
  }
}
