package com.example.townmarket.common.domain.email.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "emailVerify", timeToLive = 300000)
public class EmailVerify {

  @Id
  private String email;

  private String code;

  @Builder
  public EmailVerify(String email, String code) {
    this.email = email;
    this.code = code;
  }

  public boolean verifyCheck(String code) {
    return this.code.equals(code);
  }
}
