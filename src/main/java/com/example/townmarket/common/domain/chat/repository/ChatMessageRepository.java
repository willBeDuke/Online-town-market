package com.example.townmarket.common.domain.chat.repository;

import com.example.townmarket.common.domain.chat.entity.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = ChatMessage.class, idClass = Long.class)
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>,
    ChatMessageRepositoryQuery {

  List<ChatMessage> findAllByOrderBySendDateAsc();

}
