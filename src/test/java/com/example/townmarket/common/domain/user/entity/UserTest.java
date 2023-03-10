package com.example.townmarket.common.domain.user.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserTest {

  @Test
  @DisplayName("유효성 검사하기")
  void checkAuthorization() {

    // given
    User originUser = User.builder().id(1L).build();
    User user2 = User.builder().id(2L).build();

    // when
    boolean result1 = originUser.checkAuthorization(user2);
    boolean result2 = originUser.checkAuthorization(originUser);

    // then
    assertFalse(result1);
    assertTrue(result2);
  }
}