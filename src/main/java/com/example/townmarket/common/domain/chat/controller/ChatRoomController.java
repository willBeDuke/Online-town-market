package com.example.townmarket.common.domain.chat.controller;

import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_CREATED;

import com.example.townmarket.common.domain.chat.dto.ChatMessageDto;
import com.example.townmarket.common.domain.chat.dto.ChatRoomResponse;
import com.example.townmarket.common.domain.chat.service.ChatRoomService;
import com.example.townmarket.common.dto.StatusResponse;
import com.example.townmarket.common.enums.ResponseMessages;
import com.example.townmarket.common.security.UserDetailsImpl;
import com.example.townmarket.common.util.SetHttpHeaders;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

  private final ChatRoomService roomService;
  private final SetHttpHeaders httpHeaders;

  /* 채팅방 등록 */
  @PostMapping("/chatroom/{productId}")
  public ResponseEntity<StatusResponse> createRoom(@PathVariable Long productId,
      @AuthenticationPrincipal UserDetails userDetails) {
    roomService.createRoom(productId, userDetails.getUsername());
    return RESPONSE_CREATED;
  }

  @GetMapping("chatroom/check/{productId}")
  public ResponseEntity<Boolean> checkRoom(@PathVariable Long productId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return ResponseEntity.ok().headers(httpHeaders.setHeaderTypeJson())
        .body(roomService.checkRoom(productId, userDetails.getUser()));
  }

  /* 해당 채팅방 보기 */
  @GetMapping("/chatrooms/{roomId}")
  public ResponseEntity<List<ChatMessageDto>> getChatRoom(@PathVariable Long roomId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    List<ChatMessageDto> room = roomService.getChatRoom(roomId, userDetails.getUsername());
    return ResponseEntity.status(HttpStatus.OK).body(room);
  }

  /*나의 채팅 리스트*/
  @GetMapping("/chatroom/buy")
  public ResponseEntity<List<ChatRoomResponse>> buyChatList(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    roomService.buyChatList(userDetails.getUserId());
    return ResponseEntity.status(HttpStatus.OK)
        .body(roomService.buyChatList(userDetails.getUserId()));
  }

  @GetMapping("/chatroom/sell")
  public ResponseEntity<List<ChatRoomResponse>> sellChatList(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    roomService.sellChatList(userDetails.getUserId());
    return ResponseEntity.status(HttpStatus.OK)
        .body(roomService.sellChatList(userDetails.getUserId()));
  }

  /* 채팅방 삭제 */
  @DeleteMapping("/chatroom/{roomId}")
  public ResponseEntity<StatusResponse> deleteChat(@PathVariable Long roomId,
      @AuthenticationPrincipal
      UserDetailsImpl userDetails) {
    roomService.deleteChat(roomId, userDetails.getUsername());
    StatusResponse statusResponse = StatusResponse.valueOf(ResponseMessages.DELETE_SUCCESS);
    return new ResponseEntity<>(statusResponse, HttpStatus.NO_CONTENT);
  }
}
