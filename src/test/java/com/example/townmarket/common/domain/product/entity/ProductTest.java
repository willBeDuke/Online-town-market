package com.example.townmarket.common.domain.product.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.townmarket.common.domain.chat.entity.ChatRoom;
import com.example.townmarket.common.domain.user.entity.User;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductTest {

  @Mock
  User user;

  @InjectMocks
  Product product;


  @Test
  @DisplayName("유저 아이디 상품 판매자 비교 후 일치 여부 확인")
  void checkProductWriter() {
    // given
    when(user.getId()).thenReturn(1L);

    // when
    boolean result1 = product.checkProductWriter(1L);
    boolean result2 = product.checkProductWriter(2L);

    // then
    assertTrue(result1);
    assertFalse(result2);
  }


  @Test
  @DisplayName("채팅방 가져오기")
  void getChatRooms() {

    // given
    Set<ChatRoom> chatRooms = new LinkedHashSet<>();
    ChatRoom room1 = mock(ChatRoom.class);
    ChatRoom room2 = mock(ChatRoom.class);

    chatRooms.add(room1);
    chatRooms.add(room2);

    Product product = mock(Product.class);

    when(product.getChatRooms()).thenReturn(chatRooms);

    // when
    Set<ChatRoom> result = product.getChatRooms();

    // then
    assertEquals(chatRooms, result);
  }


  @Test
  @DisplayName("ProductEnum이 판매완료인 경우 true 반환 확인")
  void checkProductEnum() {
    // given
    Product.ProductEnum productEnum1 = Product.ProductEnum.나눔;
    Product.ProductEnum productEnum2 = Product.ProductEnum.판매완료;
    Product.ProductEnum productEnum3 = Product.ProductEnum.판매_중;

    // when
    boolean result1 = product.checkProductEnum(productEnum1);
    boolean result2 = product.checkProductEnum(productEnum2);
    boolean result3 = product.checkProductEnum(productEnum3);

    // then
    assertFalse(result1);
    assertTrue(result2);
    assertFalse(result3);
  }
}