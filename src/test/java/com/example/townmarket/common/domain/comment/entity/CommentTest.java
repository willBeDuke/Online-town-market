package com.example.townmarket.common.domain.comment.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.townmarket.common.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentTest {

  @Mock
  User user;

  @InjectMocks
  Comment comment;


  @Test
  @DisplayName("주어진 유저와 댓글 작성자를 비교하기")
  void checkCommentWriter() {
    // given
    when(user.getId()).thenReturn(1L);

    // when
    boolean result = comment.checkCommentWriter(user);

    // then
    assertTrue(result);
  }
}