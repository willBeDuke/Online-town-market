package com.example.townmarket.common.domain.chat.service;

import com.example.townmarket.common.domain.chat.dto.ChatMessageDto;
import com.example.townmarket.common.domain.chat.dto.ChatRoomResponse;
import com.example.townmarket.common.domain.chat.entity.ChatRoom;
import com.example.townmarket.common.domain.chat.repository.ChatMessageRepository;
import com.example.townmarket.common.domain.chat.repository.ChatRoomRepository;
import com.example.townmarket.common.domain.product.entity.Product;
import com.example.townmarket.common.domain.product.service.ProductServiceImpl;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.domain.user.service.UserServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomServiceImpl implements ChatRoomService {

  private final ChatRoomRepository roomRepository;
  private final ChatMessageRepository chatMessageRepository;
  private final ProductServiceImpl productService;
  private final UserServiceImpl userService;

  /*채팅방 생성*/

  @Override
  @Transactional
  public void createRoom(Long productId, String username) {
    Product product = productService.findProductById(productId);
    User user = userService.findByUsername(username);
    ChatRoom chatRooms = new ChatRoom(product, user);
    roomRepository.save(chatRooms);
//    return roomRepository.searchChatRoomByUsername(username);
  }


  /* 채팅방 보기 */
  @Override
  public List<ChatMessageDto> getChatRoom(Long roomId, String username) {
    User user = userService.findByUsername(username);
    ChatRoom room = roomRepository.findById(roomId).orElseThrow(
        () -> new IllegalArgumentException("이미 삭제된 채팅방 입니다.")
    );

    if (!roomId.equals(room.getId())) {
      throw new RuntimeException("채팅방이 존재하지 않습니다.");
    }

    if (user.checkAuthorization(user)) {
      return chatMessageRepository.getChatRoomByChatRoom(room);
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인이 속한 채팅만 볼 수 있습니다.");
  }

  /*나의 채팅 리스트*/
  @Override
  public List<ChatRoomResponse> buyChatList(Long userId) {
    User user = userService.findUserById(userId);
    return roomRepository.searchChatRoomByUsername(user.getUsername());
  }

  @Override
  public List<ChatRoomResponse> sellChatList(Long userId) {
    User user = userService.findUserById(userId);
    return roomRepository.searchChatRoomBySellerName(user.getUsername());
  }

  @Override
  @Transactional(readOnly = true)
  public boolean checkRoom(Long productId, User user) {
    return roomRepository.existsByProductAndBuyer(productService.findProductById(productId), user);
  }

  /* 채팅방 삭제 */
  @Override
  public void deleteChat(Long roomId, String username) {
    User user = userService.findByUsername(username);
    ChatRoom room = roomRepository.findById(roomId).orElseThrow(
        () -> new RuntimeException("존재하지 않는 방입니다.")
    );

    if (user.checkAuthorization(user)) {
      roomRepository.deleteById(room.getId());
      return;
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인이 속한 채팅만 삭제할 수 있습니다.");
  }
}
