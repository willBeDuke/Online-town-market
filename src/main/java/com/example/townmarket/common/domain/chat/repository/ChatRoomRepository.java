package com.example.townmarket.common.domain.chat.repository;

import com.example.townmarket.common.domain.chat.entity.ChatRoom;
import com.example.townmarket.common.domain.product.entity.Product;
import com.example.townmarket.common.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = ChatRoom.class, idClass = Long.class)
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryQuery {

  boolean existsByProductAndBuyer(Product product, User user);
}
