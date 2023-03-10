package com.example.townmarket.common.domain.board.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.townmarket.common.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BoardTest {

  @Mock
  User user;

  @Test
  @DisplayName("게시글 작성 유저 확인 테스트")
  void checkBoardWriter() {
    // given
    Board board = Board.builder()
        .title("title1")
        .content("content1")
        .subject(Board.BoardSubject.공지사항)
        .user(user)
        .build();

    when(user.getId()).thenReturn(1L);

    // when
    boolean result = board.checkBoardWriter(user);

    // then
    assertTrue(result);
  }
}