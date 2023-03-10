package com.example.townmarket.common.domain.chat.controller;

import com.example.townmarket.common.domain.chat.dto.ChatMessageDto;
import com.example.townmarket.common.domain.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatMessageController {

  private final ChatMessageService messageService;


  @CrossOrigin(value = "https://www.knock-knock.shop", methods = RequestMethod.GET)
  @MessageMapping("/{roomId}")
  @SendTo("/sub/{roomId}")
  public ChatMessageDto chatMessage(@DestinationVariable Long roomId, ChatMessageDto message) {

    messageService.createChat(message, roomId);

//    messagingTemplate.convertAndSend(
//        "/sub/" + message.getReceiver() + "/product" + message.getProductId(), message);
    return message;
  }

}
