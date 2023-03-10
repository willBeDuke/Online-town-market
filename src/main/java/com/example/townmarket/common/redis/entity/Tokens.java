package com.example.townmarket.common.redis.entity;


import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "AccessRefreshTK", timeToLive = 60 * 60 * 60 * 1000)
public class Tokens {

  @Id
  private String id;

  private String refreshToken;

  @Builder
  public Tokens(String token, String id) {
    this.id = id;
    this.refreshToken = token;
  }
  public boolean checkRefreshToken(String token) {
    return refreshToken.equals(token);
  }

}
