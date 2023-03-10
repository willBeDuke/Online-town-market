package com.example.townmarket.common.domain.chat.repository;

import static com.example.townmarket.common.domain.chat.entity.QChatRoom.chatRoom;
import static com.example.townmarket.common.domain.product.entity.QProduct.product;

import com.example.townmarket.common.domain.chat.dto.ChatRoomResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class ChatRoomRepositoryQueryImpl implements ChatRoomRepositoryQuery {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  @Transactional(readOnly = true)
  public List<ChatRoomResponse> searchChatRoomByUsername(String username) {
    return jpaQueryFactory.select(Projections.constructor(ChatRoomResponse.class,
            chatRoom.product.id,
            chatRoom.product.user,
            chatRoom.product.productName,
            chatRoom.id
        )).from(chatRoom)
        .where(chatRoom.buyer.username.eq(username))
        .leftJoin(chatRoom.product)
        .leftJoin(product.user)
        .setHint("org.hibernate.readOnly", true)
        .fetch();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ChatRoomResponse> searchChatRoomByproductUsername(String username) {
    return jpaQueryFactory.select(Projections.constructor(ChatRoomResponse.class,
            chatRoom.product.id,
            chatRoom.product.user,
            chatRoom.product.productName,
            chatRoom.id
        )).from(chatRoom)
        .where(chatRoom.product.user.username.eq(username))
        .leftJoin(chatRoom.product)
        .leftJoin(product.user)
        .setHint("org.hibernate.readOnly", true)
        .fetch();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ChatRoomResponse> searchChatRoomBySellerName(String username) {
    return jpaQueryFactory.select(Projections.constructor(ChatRoomResponse.class,
            chatRoom.product.id,
            chatRoom.buyer,
            chatRoom.product.productName,
            chatRoom.id
        )).from(chatRoom)
        .where(chatRoom.product.user.username.eq(username))
        .leftJoin(chatRoom.product)
        .leftJoin(product.user)
        .setHint("org.hibernate.readOnly", true)
        .fetch();
  }

}
